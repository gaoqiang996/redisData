package com.redisdata.gq.entity;

import lombok.Data;

/**
 * @author gq
 * @description:
 * @date 15:36 2021/12/28
 */
@Data
public class RedisData {
    /**
     * redis key
    */
    private String redisDataKey;
    /**
     * redis value
     */
    private String redisDataValue;
    /**
     * 开始时间
     */
    private String startDate;
    /**
     * 结束时间
     */
    private String endDate;
    /**
     * 时间间隔
     */
    private int interval;
    /**
     * 丢失比例
     */
    private String rate;
}
