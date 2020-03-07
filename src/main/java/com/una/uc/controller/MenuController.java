package com.una.uc.controller;

import com.una.uc.entity.AdminMenu;
import com.una.uc.service.AdminMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Una
 * @date 2020/3/6 20:32
 */
@Controller
public class MenuController{
    @Autowired
    AdminMenuService adminMenuService;

    @GetMapping("/api/menu")
    @ResponseBody
    public List<AdminMenu> menu() {
        List<AdminMenu> menus = adminMenuService.getMenusByCurrentUser();
        return menus;
    }

    @GetMapping("/api/admin/role/menu")
    public List<AdminMenu> listAllMenus() {
        List<AdminMenu> menus = adminMenuService.getMenusByRoleId(1);
        return menus;
    }
}
