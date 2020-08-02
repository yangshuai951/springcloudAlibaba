package com.ext.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ext.springcloud.entity.Order;
import com.ext.springcloud.result.CommonResult;
import com.ext.springcloud.sentinel.fallback.CustomerFallback;
import com.ext.springcloud.sentinel.handler.CustomerBlockHandler;
import com.ext.springcloud.service.OrderService;
import com.ext.springcloud.utils.IdGeneratorSnowflake;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
//@RefreshScope
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 测试
     *
     * @return
     */
    @RequestMapping("/testA")
    @SentinelResource(value = "testA",
            blockHandlerClass = CustomerBlockHandler.class, blockHandler = "textHandlerException",
            fallbackClass = CustomerFallback.class, fallback = "testFallbackException")
    public String testA() {
        int i = 1 / 0;
        return "testA~~~~";
    }

    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    @PostMapping("/order/create")
    @SentinelResource(value = "orderCreate",
            blockHandlerClass = CustomerBlockHandler.class, blockHandler = "createHandlerException",
            fallbackClass = CustomerFallback.class, fallback = "createFallbackException")
    public CommonResult create(@RequestBody Order order) {
        order.setId(IdGeneratorSnowflake.snowflakeId());
        orderService.create(order);
        return new CommonResult(200, "订单创建成功");
    }
}
