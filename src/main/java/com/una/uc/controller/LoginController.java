package com.una.uc.controller;

import com.una.uc.pojo.User;
import com.una.uc.result.Result;
import com.una.uc.result.ResultFactory;
import com.una.uc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestParam("username")String username, @RequestParam("password")String password) {
        // 对 html 标签进行转义，防止 XSS 攻击
        username = HtmlUtils.htmlEscape(username);
        password = HtmlUtils.htmlEscape(password);

        User user = userService.get(username, password);
        if (null == user) {
            String message = "账号或密码错误";
            return ResultFactory.buildFailResult(message);
        } else {
            return ResultFactory.buildSuccessResult("成功");
        }
    }
}
