package com.redisdata.gq.controller;

import com.redisdata.gq.utils.RedisUtil;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author gq
 * @description:
 * @date 17:48 2022/1/7
 */
public class FlinkController {
    @Autowired
    private RedisUtil redisUtil;

    //获取环境
    ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
//    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    //获取数据源
    List<String> json = redisUtil.getKeys("device:payload:"+"*");

    DataStreamSource<String> elementsSource = env.

}
