package com.una.uc.controller;

import com.una.uc.common.Constant;
import com.una.uc.entity.User;
import com.una.uc.common.Result;
import com.una.uc.common.ResultFactory;
import com.una.uc.realm.LoginType;
import com.una.uc.realm.UserToken;
import com.una.uc.service.UserService;
import com.una.uc.util.RedisUtil;
import com.una.uc.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @Resource
    private RedisUtil redisUtil;

    @GetMapping(value = "/api/common/login")
    public Result login(@RequestParam("account") String account, @RequestParam("password") String password) {
        account = HtmlUtils.htmlEscape(account);
        Subject subject = SecurityUtils.getSubject();
        UserToken token = new UserToken(LoginType.USER_PASSWORD, account, password);
        token.setRememberMe(true);
        try {
            subject.login(token);
            return ResultFactory.buildSuccessResult(token);
        } catch (AuthenticationException e) {
            String message = "账号或密码错误";
            return ResultFactory.buildFailResult(message);
        }
    }

    @GetMapping(value = "/api/common/phoneLogin")
    public Result phoneLogin(@RequestParam("phone") String phone, @RequestParam("verificationCode") String requestVerificationCode) {
        phone = HtmlUtils.htmlEscape(phone);
        requestVerificationCode = HtmlUtils.htmlEscape(requestVerificationCode);
        Subject subject = SecurityUtils.getSubject();
        UserToken token = new UserToken(LoginType.USER_PHONE, phone, requestVerificationCode);
        token.setRememberMe(true);
        try {
            subject.login(token);
            return ResultFactory.buildSuccessResult(token);
        } catch (AuthenticationException e) {
            String message = "账号或密码错误";
            return ResultFactory.buildFailResult(message);
        }
    }

    @PostMapping(value = "/api/common/register")
    public Result register(@RequestBody User user,@RequestParam() String verificationCode) {
        log.info("---------------- 注册新用户 ----------------------");
        log.info("---------------- 验证验证码 ----------------------");
        Object redisVerificationCode = redisUtil.get(user.getPhone() + Constant.SMS_Verification_Code.code);
        if (ObjectUtils.isEmpty(redisVerificationCode)) {
            String message = "验证码超时,请重新获取";
            return ResultFactory.buildFailResult(message);
        } else if (!redisVerificationCode.equals(verificationCode)){
            String message = "验证码错误";
            return ResultFactory.buildFailResult(message);
        }
        String message = userService.register(user);
        if ("注册成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @GetMapping(value = "/api/common/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        String account = SecurityUtils.getSubject().getPrincipal().toString();
        subject.logout();
        log.info("---------------- 账号： " + account + " 成功登出 ----------------------");
        String message = "成功登出";
        return ResultFactory.buildSuccessResult(message);
    }

    @GetMapping(value = "/api/common/getVerificationCode")
    public Result getVerificationCode(@RequestParam("phone")String phone,@RequestParam("count")int count) {
        log.info("---------------- 获取验证码 ----------------------");
        phone = HtmlUtils.htmlEscape(phone);
        String verificationCode;
        //1. 判断是否缓存该账号验证码
        Object redisVerificationCode = redisUtil.get(phone + Constant.SMS_Verification_Code.code);
        if (!ObjectUtils.isEmpty(redisVerificationCode)) {
            verificationCode = redisVerificationCode.toString();
        } else {
            verificationCode = CommonUtil.createCode(count);
        }
        //2.存入redis缓存
        boolean isSuccess = redisUtil.set(phone + Constant.SMS_Verification_Code.code, verificationCode, 180);
        if (true == isSuccess) {
            log.info("---------------- " + phone +" 验证码成功存入redis ----------------------");
            //3.发送短信
            //String isSend = TencentAPI.sendSms(phone, code);
            return ResultFactory.buildSuccessResult(verificationCode);
        } else {
            String message = "失败";
            return ResultFactory.buildFailResult(message);
        }
    }

    @GetMapping(value = "/api/common/verifyVerificationCode")
    public Result verifyVerificationCode(@RequestParam("phone")String phone,@RequestParam("verificationCode")String requestVerificationCode) {
        log.info("---------------- 验证验证码 ----------------------");
        phone = HtmlUtils.htmlEscape(phone);
        requestVerificationCode = HtmlUtils.htmlEscape(requestVerificationCode);
        Object redisVerificationCode = redisUtil.get(phone + Constant.SMS_Verification_Code.code);
        if (ObjectUtils.isEmpty(redisVerificationCode)) {
            String message = "验证码超时,请重新获取";
            return ResultFactory.buildFailResult(message);
        } else if (!redisVerificationCode.equals(requestVerificationCode)){
            String message = "验证码错误";
            return ResultFactory.buildFailResult(message);
        } else {
            String message = "验证成功";
            return ResultFactory.buildSuccessResult(message);
        }
    }

    @GetMapping(value = "/api/common/forgetPassword")
    public Result forgetPassword(@RequestParam("phone")String phone,
                                @RequestParam("password")String password,
                                @RequestParam("verificationCode" )String requestVerificationCode){
        log.info("---------------- 重置密码 ----------------------");
        phone = HtmlUtils.htmlEscape(phone);
        if (StringUtils.isEmpty(phone)) {
            String message = "手机号为空，重置失败";
            return ResultFactory.buildFailResult(message);
        }
        if (StringUtils.isEmpty(password)) {
            String message = "密码为空，重置失败";
            return ResultFactory.buildFailResult(message);
        }
        log.info("---------------- 验证验证码 ----------------------");
        Object redisVerificationCode = redisUtil.get(phone + Constant.SMS_Verification_Code.code);
        if (ObjectUtils.isEmpty(redisVerificationCode)) {
            String message = "验证码超时,请重新获取";
            return ResultFactory.buildFailResult(message);
        } else if (!redisVerificationCode.equals(requestVerificationCode)){
            String message = "验证码错误";
            return ResultFactory.buildFailResult(message);
        } else {
            User userInDB = userService.getByPhone(phone);
            if (null == userInDB) {
                String message = "手机号未注册，请先注册";
                return ResultFactory.buildFailResult(message);
            }
            // 默认生成 16 位盐
            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            int times = 2;
            String encodedPassword = new SimpleHash("md5", password, salt, times).toString();

            userInDB.setPassword(encodedPassword);
            userInDB.setSalt(salt);

            userService.addOrUpdate(userInDB);
            String message = "重置成功";

            return ResultFactory.buildSuccessResult(message);
        }
    }
}
