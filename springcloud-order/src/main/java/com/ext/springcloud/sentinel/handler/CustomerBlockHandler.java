package com.ext.springcloud.sentinel.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.ext.springcloud.entity.Order;
import com.ext.springcloud.result.CommonResult;

public class CustomerBlockHandler {

    public static CommonResult createHandlerException(Order order, BlockException exception) {
        return new CommonResult(444, "创建订单异常(限流)");
    }

    /**
     * 测试sentinel限流
     * @return
     */
    public static String textHandlerException(BlockException exception) {
        return "sentinel限流~~~~";
    }
}
