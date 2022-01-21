package com.redisdata.gq.controller;

import com.redisdata.gq.service.DataDealService;
import com.redisdata.gq.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
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


    @PostMapping("/test")
    public Result test(@RequestBody String json, HttpServletRequest request) {
//        System.out.println(getHeadersInfo(request));
        System.out.println(json);
        return Result.success();
    }

    /**
     * 描述:获取请求头内容
     */
    private String getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        String result="";
        for (String key : map.keySet()) {
            //System.out.println("key= "+ key + " and value= " + map.get(key));
            result = result + "key= "+ key + " and value= " + map.get(key)+"\n";
        }
        return result;
    }

}
