package com.redisdata.gq.service;

import java.util.List;
import java.util.Map;

/**
 * @author gq
 * @description:
 * @date 16:03 2021/12/28
 */
public interface DataDealService {
    Map getDataDeal(List<String> json,String dataKey, String startDate, String endDate, int interval);

    List<Map> getRedisData(String startDate, String endDate, int interval);
}
