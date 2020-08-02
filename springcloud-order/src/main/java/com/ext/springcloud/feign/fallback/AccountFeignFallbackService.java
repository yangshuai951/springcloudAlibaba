package com.ext.springcloud.feign.fallback;

import com.ext.springcloud.feign.AccountFeignService;
import com.ext.springcloud.result.CommonResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountFeignFallbackService implements AccountFeignService {

    @Override
    public CommonResult decrease(Long userId, BigDecimal money) {
        return new CommonResult<>(500, "用户服务异常，服务降级返回");
    }
}
