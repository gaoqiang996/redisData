package com.redisdata.gq.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author gq
 * @description:
 * @date 15:48 2021/12/28
 */
public class JacksonUtil {

//    private static final Logger logger = (Logger) LoggerFactory.getLogger(JacksonUtil.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /*static {
        *//**
         * 默认非空不输出，时间格式
         *//*
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }*/

    /**
     * 将 Java 对象转为 JSON 字符串
     */
    public static <T> String toJSON(T obj) {
        String jsonStr;
        try {
            jsonStr = objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
//            logger.info("Java 转 JSON 出错！"+e);
            throw new RuntimeException(e);
        }
        return jsonStr;
    }

    /**
     * 将 JSON 字符串转为 Java 对象
     */
    public static <T> T fromJSON(String json, Class<T> type) {
        T obj;
        try {
            obj = objectMapper.readValue(json, type);
        } catch (Exception e) {
//            logger.info("JSON 转 Java 出错！"+ e);
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static String bean2Json(Object data) {
        try {
            String result = objectMapper.writeValueAsString(data);
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T json2Bean(String jsonData, Class<T> beanType) {
        try {
            T result = objectMapper.readValue(jsonData, beanType);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> List<T> json2List(String jsonData, Class<T> beanType) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, beanType);

        try {
            List<T> resultList = objectMapper.readValue(jsonData, javaType);
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <K, V> Map<K, V> json2Map(String jsonData, Class<K> keyType, Class<V> valueType) {
        JavaType javaType = objectMapper.getTypeFactory().constructMapType(Map.class, keyType, valueType);

        try {
            Map<K, V> resultMap = objectMapper.readValue(jsonData, javaType);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
}
