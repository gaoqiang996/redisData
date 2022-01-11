package com.redisdata.gq.service.impl;

import com.redisdata.gq.entity.RedisData;
import com.redisdata.gq.service.DataDealService;
import com.redisdata.gq.utils.CommonUtils;
import com.redisdata.gq.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * @author gq
 * @description:
 * @date 16:03 2021/12/28
 */
@Service
public class DataDealServiceImpl implements DataDealService {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Map getDataDeal(List<String> sortList,String dataKey, String startDate, String endDate, int interval) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            //数据时间戳排序
            Collections.sort(sortList);
            List<Long> list = new ArrayList();
            for (String s : sortList) {
                String key = s.split(":")[3];
                long redisValue = Long.parseLong(key);
                Date sixHour = sdf.parse(startDate);
                Date nineHour = sdf.parse(endDate);
                //根据时间段写入新数组list
                if (redisValue >= sixHour.getTime() && redisValue <= nineHour.getTime()) {
                    list.add(Long.valueOf(key));
                }
            }

            List<Long> returnList = new ArrayList<>();
            System.out.println("设备:"+dataKey+" 总数量:"+sortList.size()+" 时间段内数量:" + list.size());
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    //判断相邻两个值是否大于间隔时间
                    if (list.get(i + 1) - list.get(i) > interval*1000) {
                        //判断是否完成循环比对
                        if (returnList.size() > 0 && list.get(i) < returnList.get(returnList.size() - 1)) {
                            break;
                        }
                        System.out.println("设备:"+dataKey+"时间戳：  " + list.get(i));
                        returnList.add(list.get(i + 1));
                    }
                }
            }
            System.out.println("设备:"+dataKey+"间隔"+interval+"秒数量："+returnList.size());
            int a = returnList.size();
            int b = list.size();
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberFormat.setMaximumFractionDigits(2);
            String rate = numberFormat.format((float) a/(float) b * 100)+"%";
            Map map = new HashMap();
            map.put("list",returnList);
            map.put("rate",returnList.size()==0?0:rate);
            map.put("msg","设备:"+dataKey+",总数量:"+sortList.size()+",满足时间段数量:" + list.size()+",间隔数量:"+returnList.size());
            return map;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map> getRedisData(String startDate, String endDate, int interval) {
        List<String> json = redisUtil.getKeys("device:payload:"+"*");
        if (json == null) {
            return null;
        }
        List<RedisData> groupDevice = new ArrayList<>();
        for (String s :
                json) {
            RedisData redisData = new RedisData();
            redisData.setRedisDataKey(s.split(":")[2]);
            redisData.setRedisDataValue(s);
            groupDevice.add(redisData);
        }
        Map<Long, List<RedisData>> map = CommonUtils.group(groupDevice, new CommonUtils.GroupBy<Long>() {
            @Override
            public Long groupby(Object obj) {
                RedisData d = (RedisData) obj;
                return Long.valueOf(d.getRedisDataKey()); // 分组依据为课程ID
            }
        });

        List<Map> mapList = new ArrayList<>();
        Map dataDeal;
        for (Long key : map.keySet()) {
            List<String> list = map.get(key).stream().map(RedisData::getRedisDataValue).collect(Collectors.toList());
            td t1 = new td(list,key.toString(),startDate, endDate, interval);
            try {
                dataDeal = t1.call();
                dataDeal.put("device",key);
                mapList.add(dataDeal);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mapList;
    }


    class td implements Callable {
        List<String> list;
        String key;
        String startDate;
        String endDate;
        int interval;
        public td(List<String> list, String key, String startDate, String endDate, int interval) {
            this.list= list;
            this.key = key;
            this.startDate = startDate;
            this.endDate = endDate;
            this.interval = interval;
        }

        @Override
        public Map call() throws Exception {
            Map dataDeal = getDataDeal(list,key,startDate, endDate, interval);
            return dataDeal;
        }
    }

}
