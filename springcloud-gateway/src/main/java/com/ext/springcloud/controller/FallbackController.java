package com.ext.springcloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    /**
     * 调用下游服务超时回调此方法
     *
     * @return
     */
    @RequestMapping("/fallback")
    public String fallback() {
        return "error time out";
    }
}
