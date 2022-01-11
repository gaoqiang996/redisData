package com.redisdata.gq.controller;

import com.redisdata.gq.service.DataDealService;
import com.redisdata.gq.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author gq
 * @description:
 * @date 15:34 2021/12/28
 */
@RestController
public class DataController {
    @Autowired
    private DataDealService dataDealService;

    @GetMapping("/getData")
    public Result getJsonDataFromRedis(@RequestParam(value = "startDate") String startDate,
                                           @RequestParam(value = "endDate") String endDate,
                                           @RequestParam(value = "interval") int interval) {
        List<Map> mapList = dataDealService.getRedisData(startDate, endDate, interval);
        return Result.success(mapList);
    }


}
