package com.una.uc.controller;

import com.una.uc.common.Result;
import com.una.uc.common.ResultFactory;
import com.una.uc.entity.AdminRole;
import com.una.uc.entity.User;
import com.una.uc.entity.UserInfo;
import com.una.uc.service.AdminRoleService;
import com.una.uc.service.AdminUserRoleService;
import com.una.uc.service.UserInfoService;
import com.una.uc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    UserInfoService userInfoService;

    /** -------------------------- 用户 -------------------------------------- **/

    @PostMapping(value = "/api/admin/user/add")
    public Result addUser(@RequestBody User user) {
        log.info("---------------- 增加用户 ----------------------");
        String message = userService.register(user);
        if ("注册成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @GetMapping(value = "/api/admin/user/delete")
    public Result deleteUser(@RequestParam int uid) {
        log.info("---------------- 删除用户 ----------------------");
        String message = userService.delete(uid);
        if ("删除成功".equals(message)) {
            return ResultFactory.buildSuccessResult(message);
        } else {
            return ResultFactory.buildFailResult(message);
        }
    }

    @PostMapping(value = "/api/admin/user/delete")
    public Result batchDeleteUser(@RequestBody LinkedHashMap userIds) {
        log.info("---------------- 批量删除用户 ----------------------");
        String message = userService.batchDelete(userIds);
        if ("删除成功".equals(message)) {
            return ResultFactory.buildSuccessResult(message);
        } else {
            return ResultFactory.buildFailResult(message);
        }
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

    @PutMapping(value = "/api/admin/user/status")
    public Result updateUserStatus(@RequestBody User requestUser) {
        log.info("---------------- 更新用户状态 ----------------------");
        String message = userService.updateStatus(requestUser);
        if ("更新成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
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

    @GetMapping(value = "/api/admin/user/search")
    public Result search(@RequestParam String keywords){
        log.info("---------------- 搜索用户 ----------------------");
        List<User> us = userService.search(keywords);
        return ResultFactory.buildSuccessResult(us);
    }

    @GetMapping(value = "/api/admin/user/all")
    public Result listUsers() {
        log.info("---------------- 获取所有用户 ----------------------");
        List<User> us = userService.list();
        return ResultFactory.buildSuccessResult(us);
    }

    @GetMapping(value = "/api/admin/user/current")
    public User user() {
        log.info("---------------- 获取当前登陆用户 ----------------------");
        User user =userService.getCurrentUser();
        return user;
    }

    @GetMapping(value = "/api/admin/user/role")
    public Result getAllRoleIsEnabled() {
        log.info("---------------- 获取所有角色 ----------------------");
        List<AdminRole> ad = adminRoleService.listIsEnabled();
        return ResultFactory.buildSuccessResult(ad);
    }


    /** -------------------------- 用户信息 -------------------------------------- **/

    @GetMapping(value = "/api/userInfo")
    public Result getCurrentUserInfo() {
        log.info("---------------- 获取当前用户信息 ----------------------");
        UserInfo userInfo = userInfoService.getByCurrentUser();
        return ResultFactory.buildSuccessResult(userInfo);
    }

    @PutMapping(value = "/api/userInfo")
    public Result editUserInfo(@RequestBody UserInfo userInfo) {
        log.info("---------------- 修改用户信息 ----------------------");
        String message = userInfoService.edit(userInfo);
        if ("修改成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @PostMapping(value = "/api/userInfo/cover")
    public Result editUserInfoCover(MultipartFile file) throws Exception{
        log.info("---------------- 修改用户头像 ----------------------");
        String message = userInfoService.updateCover(file);
        if (!"更新失败".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

//    @PostMapping(value = "/api/userInfo/test")
//    public Result test(MultipartFile file) throws Exception {
//        log.info("---------------- 测试 ----------------------");
//        String folder = "E:/img";
//        File imageFolder = new File(folder);
//        File f = new File(imageFolder, CommonUtil.creatUUID() + file.getOriginalFilename()
//                .substring(file.getOriginalFilename().length() - 4));
//        if (!f.getParentFile().exists())
//            f.getParentFile().mkdirs();
//        try {
//            file.transferTo(f);
//            String imgURL = "http://localhost:8443/api/file/" + f.getName();
//            return ResultFactory.buildSuccessResult(imgURL);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResultFactory.buildFailResult("");
//        }
//    }
}
