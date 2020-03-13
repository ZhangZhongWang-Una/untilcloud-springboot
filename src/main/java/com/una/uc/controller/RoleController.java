package com.una.uc.controller;

import com.una.uc.common.Result;
import com.una.uc.common.ResultFactory;
import com.una.uc.entity.AdminPermission;
import com.una.uc.entity.AdminRole;
import com.una.uc.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Una
 * @date 2020/3/6 22:08
 */
@Slf4j
@RestController
public class RoleController {
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    AdminPermissionService adminPermissionService;
    @Autowired
    AdminRolePermissionService adminRolePermissionService;
    @Autowired
    AdminRoleMenuService adminRoleMenuService;
    @Autowired
    AdminUserRoleService adminUserRoleService;

    @GetMapping(value = "/api/admin/role/all")
    public Result listRoles(){
        log.info("---------------- 获取所有角色 ----------------------");
        List<AdminRole> ad = adminRoleService.list();
        return ResultFactory.buildSuccessResult(ad);
    }

    @PostMapping(value = "/api/admin/role/add")
    public Result addRole(@RequestBody AdminRole requestRole) {
        log.info("---------------- 添加新角色 ----------------------");
        AdminRole adminRoleInDB = adminRoleService.findByName(requestRole.getName());
        if (null != adminRoleInDB) {
            String message = "该角色已存在";
            return ResultFactory.buildFailResult(message);
        } else {
            adminRoleService.addOrUpdate(requestRole);
            String message = "添加角色成功";
            return ResultFactory.buildSuccessResult(message);
        }
    }

    @PutMapping(value = "/api/admin/role/status")
    public Result updateRoleStatus(@RequestBody AdminRole requestRole) {
        log.info("---------------- 更新角色状态 ----------------------");
        String message = adminRoleService.updateRoleStatus(requestRole);
        if (!"更新成功".equals(message)){
            return ResultFactory.buildFailResult(message);
        } else {
            return ResultFactory.buildSuccessResult(message);
        }
    }

    @PutMapping(value = "/api/admin/role/edit")
    public Result editRole(@RequestBody AdminRole requestRole) {
        log.info("---------------- 修改角色信息 ----------------------");
        String message = adminRoleService.edit(requestRole);
        if (!"修改成功".equals(message)){
            return ResultFactory.buildFailResult(message);
        } else {
            return ResultFactory.buildSuccessResult(message);
        }
    }

    @GetMapping(value = "/api/admin/role/perm")
    public Result listPerms() {
        log.info("---------------- 获取所有权限 ----------------------");
        List<AdminPermission> ap = adminPermissionService.list();
        return ResultFactory.buildSuccessResult(ap);
    }

    @PutMapping(value = "/api/admin/role/perm")
    public Result updateRolePerm(@RequestParam int rid, @RequestBody LinkedHashMap permIds) {
        log.info("---------------- 更新角色权限 ----------------------");
        String message = adminRolePermissionService.updateRolePerms(rid, permIds);
        if (!"更新成功".equals(message)){
            return ResultFactory.buildFailResult(message);
        } else {
            return ResultFactory.buildSuccessResult(message);
        }
    }

    @PutMapping(value = "/api/admin/role/menu")
    public Result updateRoleMenu(@RequestParam int rid, @RequestBody LinkedHashMap menusIds) {
        log.info("---------------- 更新角色菜单 ----------------------");
        String message = adminRoleMenuService.updateRoleMenu(rid, menusIds);
        if (!"更新成功".equals(message)){
            return ResultFactory.buildFailResult(message);
        } else {
            return ResultFactory.buildSuccessResult(message);
        }
    }

    @GetMapping(value = "/api/admin/role/delete")
    public Result deleteRole(@RequestParam int rid) {
        log.info("---------------- 删除角色 ----------------------");
        String message = adminRoleService.delete(rid);
        if ("删除成功".equals(message)) {
            return ResultFactory.buildSuccessResult(message);
        } else {
            return ResultFactory.buildFailResult(message);
        }
    }

    @PostMapping(value = "/api/admin/role/delete")
    public Result batchDeleteRole(@RequestBody LinkedHashMap roleIds) {
        log.info("---------------- 批量删除角色 ----------------------");
        String message = adminRoleService.batchDelete(roleIds);
        if ("删除成功".equals(message)) {
            return ResultFactory.buildSuccessResult(message);
        } else {
            return ResultFactory.buildFailResult(message);
        }
    }

    @GetMapping(value = "/api/admin/role/search")
    public Result search(@RequestParam String keywords){
        log.info("---------------- 搜索角色 ----------------------");
        List<AdminRole> rs = adminRoleService.search(keywords);
        return ResultFactory.buildSuccessResult(rs);
    }

    @PutMapping(value = "/api/admin/role/user")
    public Result assistUser(@RequestParam int rid, @RequestBody LinkedHashMap userIds) {
        log.info("---------------- 分配用户 ----------------------");
        String message = adminUserRoleService.assistUser(rid, userIds);
        if (!"分配成功".equals(message)){
            return ResultFactory.buildFailResult(message);
        } else {
            return ResultFactory.buildSuccessResult(message);
        }
    }
}
