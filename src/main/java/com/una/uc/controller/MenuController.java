package com.una.uc.controller;

import com.una.uc.common.Result;
import com.una.uc.common.ResultFactory;
import com.una.uc.entity.AdminMenu;
import com.una.uc.service.AdminMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Una
 * @date 2020/3/6 20:32
 */
@Slf4j
@RestController
public class MenuController{
    @Autowired
    AdminMenuService adminMenuService;

    @GetMapping("/api/menu")
    public List<AdminMenu> menu() {
        log.info("---------------- 获取当前用户菜单 ----------------------");
        List<AdminMenu> menus = adminMenuService.getMenusByCurrentUser();
        return menus;
    }

    @GetMapping("/api/admin/menu/role")
    public Result listAllMenusByRoleId(@RequestParam Integer roleId) {
        log.info("---------------- 获取角色菜单 ----------------------");
        List<AdminMenu> menus = adminMenuService.getMenusByRoleId(roleId);
        return ResultFactory.buildSuccessResult(menus);
    }

    @PostMapping("api/admin/menu/add")
    public Result addMenu(@RequestBody AdminMenu adminMenu){
        log.info("---------------- 添加菜单 ----------------------");
        String message = adminMenuService.add(adminMenu);
        if ("添加成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @GetMapping("/api/admin/menu/delete")
    public Result deleteMenu(@RequestParam int mid) {
        log.info("---------------- 删除菜单 ----------------------");
        String message = adminMenuService.delete(mid);
        if ("删除成功".equals(message)) {
            return ResultFactory.buildSuccessResult(message);
        } else {
            return ResultFactory.buildFailResult(message);
        }
    }

    @PostMapping("/api/admin/menu/delete")
    public Result batchDeleteMenu(@RequestBody LinkedHashMap menuIds) {
        log.info("---------------- 批量删除菜单 ----------------------");
        String message = adminMenuService.batchDelete(menuIds);
        if ("删除成功".equals(message)) {
            return ResultFactory.buildSuccessResult(message);
        } else {
            return ResultFactory.buildFailResult(message);
        }
    }

    @PutMapping("/api/admin/menu/edit")
    public Result editMenu(@RequestBody AdminMenu adminMenu) {
        log.info("---------------- 修改菜单 ----------------------");
        String message = adminMenuService.edit(adminMenu);
        if ("修改成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @GetMapping(value = "/api/admin/menu/search")
    public Result search(@RequestParam String keywords){
        log.info("---------------- 搜索菜单 ----------------------");
        List<AdminMenu> ms = adminMenuService.search(keywords);
        return ResultFactory.buildSuccessResult(ms);
    }

    @GetMapping(value = "/api/admin/menu/all")
    public Result all(){
        log.info("---------------- 获取所有菜单 ----------------------");
        List<AdminMenu> ms = adminMenuService.all();
        return ResultFactory.buildSuccessResult(ms);
    }
}
