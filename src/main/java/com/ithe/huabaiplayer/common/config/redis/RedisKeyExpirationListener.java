package com.ithe.huabaiplayer.common.config.redis;

import com.ithe.huabaiplayer.file.factory.FileFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.ithe.huabaiplayer.common.constant.RedisKeyConstants.DONGMAN_FENPIAN;

/**
 * @author L
 */
@Component
@Slf4j
public class RedisKeyExpirationListener implements MessageListener {

    @Autowired
    private FileFactory fileFactory;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 获取过期的 key
        String expiredKey = message.toString();
        System.out.println("Key expired: " + expiredKey);

        // 在这里执行你的业务逻辑，例如处理某个订单状态等
        handleExpiredKeyLogic(expiredKey);
    }

    private void handleExpiredKeyLogic(String key) {
        // 具体业务逻辑处理
        log.info("Handle business logic for expired key: {}", key);
        if (key.contains(DONGMAN_FENPIAN)) {
            log.info("Handle business logic for expired key: {}", key);
            String path = key.replace(DONGMAN_FENPIAN, "");
            try {
                fileFactory.getFileService().deleteFenPath(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
