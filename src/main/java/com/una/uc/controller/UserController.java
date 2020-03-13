package com.una.uc.controller;

import com.una.uc.common.Result;
import com.una.uc.common.ResultFactory;
import com.una.uc.entity.User;
import com.una.uc.service.AdminUserRoleService;
import com.una.uc.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Una
 * @date 2020/3/6 22:11
 */
@RestController
public class UserController{
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;

    @GetMapping(value = "/api/admin/user")
    public List<User> listUsers() {
        return userService.list();
    }

    @GetMapping(value = "/api/admin/user/current")
    public User user() {
        User user =userService.getCurrentUser();
        return user;
    }

    @PutMapping(value = "/api/admin/user/resetPassword")
    public Result resetPassword(@RequestBody User requestUser) {
        String message = userService.resetPassword(requestUser);
        if ("重置成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @PutMapping(value = "/api/admin/user/status")
    public Result updateUserStatus(@RequestBody User requestUser) {
        String message = userService.updateStatus(requestUser);
        if ("更新成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildSuccessResult(message);
    }
//
//    @PutMapping("/api/admin/user/password")
//    public Result resetPassword(@RequestBody User requestUser) {
//        userService.resetPassword(requestUser);
//        String message = "重置密码成功";
//        return ResultFactory.buildSuccessResult(message);
//    }
//
//    @PutMapping("/api/admin/user")
//    public Result editUser(@RequestBody User requestUser) {
//        userService.editUser(requestUser);
//        String message = "修改用户信息成功";
//        return ResultFactory.buildSuccessResult(message);
//    }
}
