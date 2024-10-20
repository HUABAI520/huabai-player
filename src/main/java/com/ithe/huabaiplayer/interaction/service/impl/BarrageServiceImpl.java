package com.ithe.huabaiplayer.interaction.service.impl;

import com.ithe.huabaiplayer.common.CursorPage;
import com.ithe.huabaiplayer.interaction.mapper.BarrageMapper;
import com.ithe.huabaiplayer.interaction.model.dto.req.BarrageAddReq;
import com.ithe.huabaiplayer.interaction.model.dto.resp.BarrageResp;
import com.ithe.huabaiplayer.interaction.model.entity.Barrage;
import com.ithe.huabaiplayer.interaction.mq.RabbitMQProducer;
import com.ithe.huabaiplayer.interaction.service.BarrageService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 弹幕表 服务层实现。
 *
 * @author L
 * @since 2024-09-29
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BarrageServiceImpl extends ServiceImpl<BarrageMapper, Barrage> implements BarrageService {
    private final RabbitMQProducer rabbitMQProducer;

    // 线程安全的集合
    private final CopyOnWriteArrayList<Barrage> list = new CopyOnWriteArrayList<>();

    @Scheduled(fixedRate = 10000) // 每10秒执行一次
    public void checkTimeout() {
        if (list.isEmpty()) {
            return;
        }
        log.info("定时处理了收集消息{}", list);
        processMessages();
    }

    @Override
    public void saveByTime(Barrage message) {
        list.add(message);
        if (list.size() >= 20) {
            log.info("超过阈值处理了收集的消息{}", list);
            processMessages();
        }
    }


    private void processMessages() {
        synchronized (list) {
            if (!list.isEmpty()) {
                // 清空消息列表和重置计数器
                this.saveBatch(list);
                list.clear();
            }
        }
    }

    @Override
    public void addBarrage(BarrageAddReq add, Long id) {
        Barrage barrage = BarrageAddReq.to(add);
        barrage.setUserId(id);
        save(barrage);
    }

    @Override
    public void addBarrage(BarrageAddReq add, Long id, String type) {
        Barrage barrage = BarrageAddReq.to(add);
        barrage.setUserId(id);
        barrage.setCreateTime(new Date());
        rabbitMQProducer.sendMessage(barrage, type);
    }


    /**
     * 直接默认分页大小 1000
     * 使用游标
     * maxCursor 上次最大的id
     * todo 后面考虑大弹幕列表时 使用xml文件存储
     */
    @Override
    @Cacheable(cacheNames = "barrage",
            key = "'barrage_' + #videoId + '_' + #maxCursor",
            cacheManager = "stringRedisManager")
    public CursorPage<BarrageResp> getBarrages(Long videoId, Long maxCursor) {
        QueryWrapper queryWrapper = query().eq(Barrage::getVideoId, videoId).gt(Barrage::getId, maxCursor);
        Page<Barrage> page = this.page(new Page<>(1, 1000), queryWrapper);
        if (page.getTotalRow() <= 0) {
            return CursorPage.empty();
        }
        CursorPage<BarrageResp> cursorPage = new CursorPage<>();
        cursorPage.setHasNext(page.getTotalPage() > 1);
        cursorPage.setMaxCursor(page.getRecords().get(page.getRecords().size() - 1).getId());
        cursorPage.setData(page.getRecords().stream().map(BarrageResp::of).toList());
        return cursorPage;
    }

}
