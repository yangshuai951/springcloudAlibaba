package com.ext.springcloud.feign.fallback;

import com.ext.springcloud.feign.StorageFeignService;
import com.ext.springcloud.result.CommonResult;
import org.springframework.stereotype.Component;

@Component
public class StorageFeignFallbackService implements StorageFeignService {

    @Override
    public CommonResult decrease(Long productId, Integer count) {
        return new CommonResult<>(500, "仓库服务异常，服务降级返回");
    }
}
