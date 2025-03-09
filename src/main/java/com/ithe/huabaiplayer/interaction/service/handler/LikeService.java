package com.ithe.huabaiplayer.interaction.service.handler;

import com.ithe.huabaiplayer.interaction.model.entity.LikeCounts;
import com.ithe.huabaiplayer.interaction.model.entity.Likes;
import com.ithe.huabaiplayer.interaction.model.enums.LikeTypeEnum;
import com.ithe.huabaiplayer.interaction.service.LikeCountsService;
import com.ithe.huabaiplayer.interaction.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName LikeService
 * @Author hua bai
 * @Date 2024/9/29 14:29
 * 点赞的各自操作的redis实现
 **/
@Service
@RequiredArgsConstructor
public class LikeService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final LikeCountsService likeCountsService;
    private final LikesService likesService;

    private static final String LIKE_COUNT_KEY = "like_count:";
    private static final String LIKE_LIST_KEY = "like_list:";

    public String getLikeCountKey(LikeTypeEnum typeEnum, Long thirdId) {
        return LIKE_COUNT_KEY + typeEnum.getType() + ":" + thirdId;
    }

    public String getLikeListKey(LikeTypeEnum typeEnum, Long thirdId) {
        return LIKE_LIST_KEY + typeEnum.getType() + ":" + thirdId;
    }

    public String getLikeListPrefix(LikeTypeEnum typeEnum) {
        return LIKE_LIST_KEY + typeEnum.getType() + ":";
    }

    // 用户点赞
    public Boolean like(Long userId, LikeTypeEnum typeEnum, Long thirdId) {
        return likeList(userId, typeEnum, thirdId);

    }

    // 用户取消赞
    public Boolean unlike(Long userId, LikeTypeEnum typeEnum, Long thirdId) {
        return unlikeList(userId, typeEnum, thirdId);
    }

    public Boolean unlikeList(Long userId, LikeTypeEnum typeEnum, Long thirdId) {
        String listKey = getLikeListKey(typeEnum, thirdId);
        Double score = redisTemplate.opsForZSet().score(listKey, userId);
        if (score == null) {
            // 存在缓存不存在的情况但是设置前端不出错 则表示是删除
            redisTemplate.opsForZSet().add(listKey, userId, -1);
            return true;
        }
        Integer isLike = score.intValue();
        if (isLike.equals(1) || isLike.equals(2)) {
            isLike = isLike.equals(1) ? -1 : 0;
            redisTemplate.opsForZSet().add(listKey, userId, isLike);
            return true;
        }
        return false;
    }

    public Boolean likeList(Long userId, LikeTypeEnum typeEnum, Long thirdId) {
        // 点赞列表
        String listKey = getLikeListKey(typeEnum, thirdId);
        Double score = redisTemplate.opsForZSet().score(listKey, userId);
        if (score == null) {
            redisTemplate.opsForZSet().add(listKey, userId, 2);
            return true;
        }
        Integer isLike = score.intValue();
        if (isLike.equals(0) || isLike.equals(-1)) {
            // 0 表示数据库没有这个用户和这个id 的点赞关系 -1 表示数据库有 但是删除了存储到redis中 后续更新数据库
            // 1 表示数据库有点赞关系不用更新 2 表示是新的点赞关系 需要更新到数据库中
            isLike = isLike.equals(0) ? 2 : 1;
            redisTemplate.opsForZSet().add(listKey, userId, isLike);
            return true;
        }
        return false;
    }

    public List<LikeCounts> selectCounts(List<Long> thirds, LikeTypeEnum likeTypeEnum) {
        List<LikeCounts> list = new ArrayList<>();
        List<Long> noCache = new ArrayList<>();
        // 先从缓存查询 没有的再从数据库中查询
        for (Long third : thirds) {
            String countKey = getLikeCountKey(likeTypeEnum, third);
            Integer count = (Integer) redisTemplate.opsForValue().get(countKey);
            if (count == null) {
                noCache.add(third);
            } else {
                list.add(LikeCounts.builder().thirdType(likeTypeEnum.getType()).thirdId(third).count(count).build());
            }
        }
        if (!noCache.isEmpty()) {
            List<LikeCounts> newList = likeCountsService.queryChain()
                    .in(LikeCounts::getThirdId, noCache)
                    .eq(LikeCounts::getThirdType, likeTypeEnum.getType())
                    .list();
            // 添加到cache 并设置过期时间2min
            newList.forEach(item ->
                    redisTemplate.opsForValue().set(
                            getLikeCountKey(likeTypeEnum, item.getThirdId()), item.getCount(), 2, TimeUnit.MINUTES
                    ));
            list.addAll(newList);
        }
        return list;
    }

    public Map<Long, Boolean> selectIsLike(Long userId, LikeTypeEnum likeTypeEnum, List<Long> thirds) {
        List<Long> noCache = new ArrayList<>();
        // 0 表示数据库没有这个用户和这个id 的点赞关系 -1 表示数据库有 但是删除了存储到redis中 后续更新数据库
        // 1 表示数据库有点赞关系不用更新 2 表示是新的点赞关系 需要更新到数据库中
        Map<Long, Boolean> list = new HashMap<>();
        for (Long third : thirds) {
            String countKey = getLikeListKey(likeTypeEnum, third);
            Double score = redisTemplate.opsForZSet().score(countKey, userId);
            if (score == null) {
                noCache.add(third);
            } else {
                Integer isLike = score.intValue();
                if (isLike.equals(0) || isLike.equals(-1)) {
                    list.put(third, false);
                } else {
                    list.put(third, true);
                }
            }
        }
        if (!noCache.isEmpty()) {
            // thirdId -> Likes
            Map<Long, Likes> collect = likesService.queryChain()
                    .in(Likes::getThirdId, noCache)
                    .eq(Likes::getThirdType, likeTypeEnum.getType())
                    .eq(Likes::getUserId, userId)
                    .list().stream().collect(Collectors.toMap(Likes::getThirdId, o -> o));
            //因为collect会没有noCache的 所有遍历noCache
            for (Long third : noCache) {
                Likes likes = collect.get(third);
                String countKey = getLikeListKey(likeTypeEnum, third);
                if (likes == null) {
                    redisTemplate.opsForZSet().add(countKey, userId, 0);
                    list.put(third, false);
                } else {
                    redisTemplate.opsForZSet().add(countKey, userId, 1);
                    list.put(third, true);
                }
            }
        }
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    public void likeCoTask() {
        String prefix = getLikeListPrefix(LikeTypeEnum.COMMENT);
//        Set<String> keys = redisTemplate.keys(prefix);
        Set<String> keys = new HashSet<>(getKeysByPattern(prefix));
        if (keys.isEmpty()) {
            return;
        }
        List<Likes> adds = new ArrayList<>();
        List<Likes> des = new ArrayList<>();
        List<LikeCounts> counts = new ArrayList<>();
        Date date = new Date();

        for (String key : keys) {
            Set<ZSetOperations.TypedTuple<Object>> result =
                    redisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
            if (result == null || result.isEmpty()) {
                continue;
            }
            int count = 0;
            Long thirdId = Long.valueOf(key.substring(prefix.length()));

            for (ZSetOperations.TypedTuple<Object> tuple : result) {
                Object value = tuple.getValue();
                Long userId;
                if (value instanceof Integer) {
                    userId = ((Integer) value).longValue();
                } else if (value instanceof Long) {
                    userId = (Long) value;
                } else {
                    assert value != null;
                    throw new IllegalStateException("Unexpected value type: " + value.getClass());
                }
                Double score = tuple.getScore(); // 获取对应的分数
                if (score == null) {
                    continue;
                }
                // 表示需要使用
                int use = score.intValue();
                if (use == -1) {
                    count--;
                    des.add(Likes.builder().thirdId(thirdId).userId(userId).thirdType(LikeTypeEnum.COMMENT.getType()).build());
                } else if (use == 2) {
                    // 生成一个随机数 0 到 120000
                    int x = new Random().nextInt(120000);
                    adds.add(Likes.builder().thirdId(thirdId).userId(userId).thirdType(LikeTypeEnum.COMMENT.getType())
                            .likeTime(DateUtils.addMilliseconds(date, -x)).build());
                    count++;
                }

            }
            // 表示点赞数需要更新吗？
            if (count != 0) {
                counts.add(LikeCounts.builder().thirdId(thirdId).count(count).thirdType(LikeTypeEnum.COMMENT.getType()).build());
            }

        }
        if (!adds.isEmpty()) {
               likesService.saveOrUpdateBatch(adds);
        }
        if (!des.isEmpty()) {
            likesService.deleteBatchSelective(des);
        }
        if (!counts.isEmpty()) {
            likeCountsService.updateOrInsertBatchSelective(counts);
        }
        // 删除所有key
        redisTemplate.delete(keys);
    }


    public List<String> getKeysByPattern(String pattern) {
        List<String> keys = new ArrayList<>();
        ScanOptions options = ScanOptions.scanOptions().match(pattern + "*").count(1000).build();
        Cursor<byte[]> cursor = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().scan(options);

        while (cursor.hasNext()) {
            String key = new String(cursor.next());
            keys.add(key);
        }
        return keys;
    }

    public void likeBaTask() {

    }

    public void likeRaTask() {

    }
}
