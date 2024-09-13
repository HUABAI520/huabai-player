package com.ithe.huabaiplayer.common.constant;

/**
 * @author L
 */
public interface RedisKeyConstants {

    String USER_DATA_PREFIX = "user:";
    String SESSION_PREFIX = "session:";
    String CACHE_PREFIX = "cache:";
    String BATCH_COUNT = "batch_count";
    String PURCHASE_COUNT = "purchase_count";
    String OFFLINEOUT = "offlineout";
    String DOCUMENT = "document";
    String USER_VO = "user_vo:";
    /**
     * 生产订单
     */
    String PRODUCE_ORDER_PREFIX = "produce_order";
    /**
     * 用户名和时间
     */
    String USERNAME_TIME = "username_time";
    String DONGMAN_FENPIAN = "dongman.fenpian_";
    String DONGMAN_INDEX = "dongman.index_";
    String MINIO_PREFIX = "minio:presignedUrl:";

}
