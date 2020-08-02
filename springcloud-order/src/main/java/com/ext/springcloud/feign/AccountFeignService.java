package com.ext.springcloud.feign;

import com.ext.springcloud.feign.fallback.AccountFeignFallbackService;
import com.ext.springcloud.result.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "springcloud-account"/*, fallback = AccountFeignFallbackService.class*/)
public interface AccountFeignService {

    /**
     * 减余额
     *
     * @param userId
     * @param money
     * @return
     */
    @PostMapping(value = "/account/decrease")
    CommonResult decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);
}
