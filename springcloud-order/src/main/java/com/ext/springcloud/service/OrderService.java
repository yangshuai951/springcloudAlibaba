package com.ext.springcloud.service;

import com.ext.springcloud.entity.Order;

public interface OrderService {

    /**
     * 创建订单
     * @param order
     */
    void create(Order order);
}
