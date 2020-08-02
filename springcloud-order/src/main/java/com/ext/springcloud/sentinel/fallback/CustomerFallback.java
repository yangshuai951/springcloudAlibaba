package com.ext.springcloud.sentinel.fallback;

import com.ext.springcloud.entity.Order;
import com.ext.springcloud.result.CommonResult;
import org.springframework.web.bind.annotation.RequestBody;

public class CustomerFallback {


    public static CommonResult createFallbackException(@RequestBody Order order, Throwable e) {
        return new CommonResult(444, "系统异常");
    }

    /**
     * 测试tests
     *
     * @return
     */
    public static String testFallbackException() {
        return "testA~~~~代码异常";
    }
}
