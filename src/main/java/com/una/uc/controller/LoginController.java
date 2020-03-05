package com.una.uc.controller;

import com.una.uc.entity.User;
import com.una.uc.result.Result;
import com.una.uc.result.ResultFactory;
import com.una.uc.service.UserService;
import com.una.uc.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;


@Controller
public class LoginController {
    @Autowired
    UserService userService;
    String verificationCode;
    long verificationCodeCreateTime;

    @GetMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestParam String account, @RequestParam String password) {
        account = HtmlUtils.htmlEscape(account);
//        String username = HtmlUtils.htmlEscape(requesetUser.getUsername());
        Subject subject = SecurityUtils.getSubject();
//        subject.getSession().setTimeout(10000);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(account, password);
        usernamePasswordToken.setRememberMe(true);
        try {
            subject.login(usernamePasswordToken);
            return ResultFactory.buildSuccessResult(usernamePasswordToken);
        } catch (AuthenticationException e) {
            String message = "账号或密码错误";
            return ResultFactory.buildFailResult(message);
        }
    }

    @PostMapping("/api/register")
    @ResponseBody
    public Result register(@RequestBody User user) {
        String message = userService.register(user);
        if ("注册成功" == message)
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @ResponseBody
    @GetMapping("api/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String message = "成功登出";
        return ResultFactory.buildSuccessResult(message);
    }

    @ResponseBody
    @GetMapping(value = "api/authentication")
    public String authentication(){
        return "身份认证成功";
    }

    @GetMapping(value = "api/getVerificationCode")
    @ResponseBody
    public Result getVerificationCode(@RequestParam("phone")String phone,@RequestParam("count")int count) {
        phone = HtmlUtils.htmlEscape(phone);
        verificationCode = StringUtils.createCode(count);
        verificationCodeCreateTime = System.currentTimeMillis();

        return ResultFactory.buildSuccessResult(verificationCode);
//        String temp = TencentAPI.sendSms(phone, code);
//        if ("失败".equals(temp)){
//            return ResultFactory.buildFailResult(temp);
//        } else {
//            String message = "验证码发送成功";
//            return ResultFactory.buildSuccessResult(message);
//        }
    }

    @GetMapping(value = "api/verifyVerificationCode")
    @ResponseBody
    public Result verifyVerificationCode(@RequestParam("verificationCode")String requestVerificationCode) {
        requestVerificationCode = HtmlUtils.htmlEscape(requestVerificationCode);
        if (null == verificationCode) {
            String message = "请获取验证码";
            return ResultFactory.buildFailResult(message);
        }else if (((System.currentTimeMillis() - verificationCodeCreateTime) / 1000) > 120 ){
            String message = "验证码超时";
            return ResultFactory.buildFailResult(message);
        }else if (!verificationCode.equals(requestVerificationCode)) {
            String message = "验证码错误";
            return ResultFactory.buildFailResult(message);
        } else {
            String message = "验证成功";
            return ResultFactory.buildSuccessResult(message);
        }
    }

}
