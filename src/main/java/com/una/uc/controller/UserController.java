package com.una.uc.controller;

import com.una.uc.common.Result;
import com.una.uc.common.ResultFactory;
import com.una.uc.entity.User;
import com.una.uc.service.AdminUserRoleService;
import com.una.uc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Una
 * @date 2020/3/6 22:11
 */
@Slf4j
@RestController
public class UserController{
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;

    @GetMapping(value = "/api/admin/user/all")
    public List<User> listUsers() {
        log.info("---------------- 获取所有用户 ----------------------");
        return userService.list();
    }

    @GetMapping(value = "/api/admin/user/current")
    public User user() {
        log.info("---------------- 获取当前登陆用户 ----------------------");
        User user =userService.getCurrentUser();
        return user;
    }

    @PutMapping(value = "/api/admin/user/password")
    public Result resetPassword(@RequestBody User requestUser) {
        log.info("---------------- 重置密码 ----------------------");
        String message = userService.resetPassword(requestUser);
        if ("重置成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @PutMapping(value = "/api/admin/user/status")
    public Result updateUserStatus(@RequestBody User requestUser) {
        log.info("---------------- 更新用户状态 ----------------------");
        String message = userService.updateStatus(requestUser);
        if ("更新成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildSuccessResult(message);
    }

    @PutMapping("/api/admin/user/edit")
    public Result editUser(@RequestBody User requestUser) {
        log.info("---------------- 修改用户信息 ----------------------");
        String message = userService.editUser(requestUser);
        if ("修改成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @GetMapping(value = "/api/admin/user/search")
    public Result search(@RequestParam String keywords){
        log.info("---------------- 搜索用户 ----------------------");
        List<User> us = userService.search(keywords);
        return ResultFactory.buildSuccessResult(us);
    }

    @GetMapping(value = "/api/admin/user/delete")
    public Result deleteRole(@RequestParam int uid) {
        log.info("---------------- 删除用户 ----------------------");
        String message = userService.delete(uid);
        if ("删除成功".equals(message)) {
            return ResultFactory.buildSuccessResult(message);
        } else {
            return ResultFactory.buildFailResult(message);
        }
    }

    @PostMapping(value = "/api/admin/user/delete")
    public Result batchDeleteRole(@RequestBody LinkedHashMap userIds) {
        log.info("---------------- 批量删除角色 ----------------------");
        String message = userService.batchDelete(userIds);
        if ("删除成功".equals(message)) {
            return ResultFactory.buildSuccessResult(message);
        } else {
            return ResultFactory.buildFailResult(message);
        }
    }

    @PostMapping(value = "/api/admin/user/add")
    public Result add(@RequestBody User user) {
        log.info("---------------- 增加新用户 ----------------------");
        String message = userService.register(user);
        if ("注册成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }
}
