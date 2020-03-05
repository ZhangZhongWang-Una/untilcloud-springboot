package com.una.uc.controller;

import com.una.uc.common.Constant;
import com.una.uc.entity.User;
import com.una.uc.common.Result;
import com.una.uc.common.ResultFactory;
import com.una.uc.service.UserService;
import com.una.uc.util.RedisUtil;
import com.una.uc.util.CommonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.hibernate.annotations.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;


@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @Resource
    private RedisUtil redisUtil;

    @GetMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestParam String account, @RequestParam String password) {
        account = HtmlUtils.htmlEscape(account);
        Subject subject = SecurityUtils.getSubject();
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
        String verificationCode;
        //1. 判断是否缓存该账号验证码
        Object redisVerificationCode = redisUtil.get(phone + Constant.SMS_Verification_Code);
        if (!ObjectUtils.isEmpty(redisVerificationCode)) {
            verificationCode = redisVerificationCode.toString();
        } else {
            verificationCode = CommonUtil.createCode(count);
        }
        //2.存入redis缓存
        boolean isSuccess = redisUtil.set(phone + Constant.SMS_Verification_Code, verificationCode, 120);
        if (true == isSuccess) {
            //3.发送短信
            //String isSend = TencentAPI.sendSms(phone, code);
            return ResultFactory.buildSuccessResult(verificationCode);
        } else {
            String message = "失败";
            return ResultFactory.buildFailResult(message);
        }

    }

    @GetMapping(value = "api/verifyVerificationCode")
    @ResponseBody
    public Result verifyVerificationCode(@RequestParam("phone")String phone,@RequestParam("verificationCode")String requestVerificationCode) {
        phone = HtmlUtils.htmlEscape(phone);
        requestVerificationCode = HtmlUtils.htmlEscape(requestVerificationCode);
        Object redisVerificationCode = redisUtil.get(phone + Constant.SMS_Verification_Code);
        if (!ObjectUtils.isEmpty(redisVerificationCode) && redisVerificationCode.equals(requestVerificationCode)) {
            String message = "验证成功";
            return ResultFactory.buildSuccessResult(message);
        } else {
            String message = "验证码错误或超时";
            return ResultFactory.buildFailResult(message);
        }
    }



}
