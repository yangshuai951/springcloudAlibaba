package com.ext.springcloud.service;

import java.math.BigDecimal;

public interface AccountService {

    void decrease(Long userId, BigDecimal money);
}
