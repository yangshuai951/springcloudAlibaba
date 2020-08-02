package com.ext.springcloud.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;

/**
 * @Author EiletXie
 * @Since 2020/3/18 21:18
 */
public class IdGeneratorSnowflake {
    private static long workerId = 0;
    private static long datacenterId = 1;
    private static Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);


    public static void init() {
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            System.out.println("当前机器的workerId:" + workerId);
        } catch (Exception e) {
            System.out.println("当前机器的workerId获取失败:" + e);
            workerId = NetUtil.getLocalhostStr().hashCode();
            System.out.println("当当前机器 workId::" + workerId);
        }

    }

    public static synchronized long snowflakeId() {
        return snowflake.nextId();
    }

    public static synchronized long snowflakeId(long workerId, long datacenterId) {
        snowflake = IdUtil.createSnowflake(workerId, datacenterId);
        long snowId = snowflake.nextId();
        init();
        return snowId;
    }

    public static void main(String[] args) {
        System.out.println(snowflakeId(1, 1));
    }
}
