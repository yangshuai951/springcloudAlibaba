package com.ext.springcloud.controller;

import com.ext.springcloud.entity.Account;
import com.ext.springcloud.result.CommonResult;
import com.ext.springcloud.service.AccountService;
import com.ext.springcloud.utils.JwtUtil;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
@RefreshScope
public class AccountController {

    @Resource
    private AccountService accountService;

    @RequestMapping("/account/decrease")
    public CommonResult decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money) {
        accountService.decrease(userId, money);
        return new CommonResult(200, "扣减账户余额成功！");
    }

    @RequestMapping("/login")
    public CommonResult login() {
        //todo 从数据库验证 后续更改
        Account account = new Account();
        account.setId(1l);
        account.setHeadImg("qqqq");
        account.setName("张三");
        String token = JwtUtil.geneJsonWebToken(account);

        return new CommonResult(200, "用户登录成功", token.toString());

    }
}
