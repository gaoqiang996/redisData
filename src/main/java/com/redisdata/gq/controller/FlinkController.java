package com.redisdata.gq.controller;

import com.redisdata.gq.utils.RedisUtil;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.util.Collector;
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

    DataSource<String> elementsSource = env.fromCollection(json);


    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
        env.setParallelism(2);
//        String hostname = "192.168.75.101";
//        int port = 5000;

        DataStreamSource<String> dataStream = null;

        SingleOutputStreamOperator<Tuple2<String, Integer>> result = dataStream.flatMap(new LineSplitter()).keyBy(0).sum(1);

        result.print();

        env.execute("test word count");

        //获取数据源
//        List<String> json = redisUtil.getKeys("device:payload:"+"*");
//        String text  = "abcda abbbb cccccc dddddddeeeeee fffffffff gggggggggggg";
//        DataSource<String> elementsSource = env.fromCollection(json);
        //通过字符串构建数据集
        DataSource<String> text = env.fromElements(
                "Who's there?",
                "I think I hear them Stand, ho! Who's there?"
        );
        //分割字符串,按照key进行分组,统计相同的key个数
        DataSet<Tuple2<String, Integer>> wordCounts = text
                .flatMap(new LineSplitter())
                .groupBy(0)//聚合
                .sum(1);//统计
        //打印
        wordCounts.print();
    }


    public static class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
            //根据空格进行切割
            for (String word : s.split(" ")) {
                collector.collect(new Tuple2<>(word, 1));
            }
        }
    }

}
