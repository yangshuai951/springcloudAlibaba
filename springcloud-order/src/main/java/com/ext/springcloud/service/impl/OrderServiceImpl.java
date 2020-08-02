package com.ext.springcloud.service.impl;

import com.ext.springcloud.dao.OrderDao;
import com.ext.springcloud.entity.Order;
import com.ext.springcloud.feign.AccountFeignService;
import com.ext.springcloud.feign.StorageFeignService;
import com.ext.springcloud.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageFeignService storageFeignService;

    @Resource
    private AccountFeignService accountFeignService;


//    **
//            * 创建订单->调用库存服务扣减库存->调用账户服务扣减账户余额->修改订单状态
//     * 简单说:
//            * 下订单->减库存->减余额->改状态
    @Override
    @GlobalTransactional(name = "fsp_create",rollbackFor = Exception.class)
    public void create(Order order) {

        // 1 新建订单
        log.info("----->开始新建订单");
        orderDao.create(order);

        // 2 扣减库存
        log.info("----->订单微服务开始调用库存,做扣减Count");
        storageFeignService.decrease(order.getProductId(), order.getCount());
        log.info("----->订单微服务开始调用库存,做扣减End");

        // 3 扣减账户
        log.info("----->订单微服务开始调用账户,做扣减Money");
        accountFeignService.decrease(order.getUserId(), order.getMoney());
        log.info("----->订单微服务开始调用账户,做扣减End");

        // 4 修改订单状态,从0到1,1代表已完成
        log.info("----->修改订单状态开始");
        orderDao.update(order.getUserId(), 0);

        log.info("----->下订单结束了,O(∩_∩)O哈哈~");

    }
}
