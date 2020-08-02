package com.ext.springcloud.feign;

import com.ext.springcloud.feign.fallback.StorageFeignFallbackService;
import com.ext.springcloud.result.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "springcloud-storage"/*, fallback = StorageFeignFallbackService.class*/)
public interface StorageFeignService {

    @PostMapping(value = "/storage/decrease")
    CommonResult decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
