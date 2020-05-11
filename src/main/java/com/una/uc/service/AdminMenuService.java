package com.una.uc.service;

import com.una.uc.dao.AdminMenuDAO;
import com.una.uc.entity.AdminMenu;
import com.una.uc.entity.AdminRoleMenu;
import com.una.uc.entity.AdminUserRole;
import com.una.uc.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Una
 * @date 2020/3/5 19:09
 */
@Service
public class AdminMenuService {
    @Autowired
    AdminMenuDAO adminMenuDAO;
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    public AdminMenu findById(int id) {
        return adminMenuDAO.findById(id);
    }

    public List<AdminMenu> getAllByParentId(int parentId) {return adminMenuDAO.findAllByParentId(parentId);}

    public List<AdminMenu> getMenusByCurrentUser() {
        String account = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.getByAccount(account);
        List<AdminUserRole> userRoleList = adminUserRoleService.listAllByUid(user.getId());
        List<AdminMenu> menus = new ArrayList<>();
        for (AdminUserRole userRole : userRoleList) {
            List<AdminRoleMenu> rms = adminRoleMenuService.findAllByRid(userRole.getRid());
            for (AdminRoleMenu rm : rms) {
                // 增加防止多角色状态下菜单重复的逻辑
                AdminMenu menu = adminMenuDAO.findById(rm.getMid());
                boolean isExist = false;
                for (AdminMenu m : menus) {
                    if (m.getId() == menu.getId()) {
                        isExist = true;
                    }
                }
                if (!isExist) {
                    menus.add(menu);
                }
            }
        }
        handleMenus(menus);
        return menus;
    }

    public List<AdminMenu> getMenusByRoleId(int rid) {
        List<AdminMenu> menus = new ArrayList<>();
        List<AdminRoleMenu> rms = adminRoleMenuService.findAllByRid(rid);
        for (AdminRoleMenu rm : rms) {
            menus.add(adminMenuDAO.findById(rm.getMid()));
        }
        handleMenus(menus);
        return menus;
    }

    public void handleMenus(List<AdminMenu> menus) {
        for (AdminMenu menu : menus) {
            menu.setChildren(getAllByParentId(menu.getId()));
        }

        Iterator<AdminMenu> iterator = menus.iterator();
        while (iterator.hasNext()) {
            AdminMenu menu = iterator.next();
            if (menu.getParentId() != 0) {
                iterator.remove();
            }
        }
    }

    public List<AdminMenu> list() {
        List<AdminMenu> menus = adminMenuDAO.findAll();
        handleMenus(menus);
        return menus;
    }
    public String add(AdminMenu adminMenu){
        String message = "";
        try {
            adminMenuDAO.save(adminMenu);
            message = "添加成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数错误，添加失败";
        }

        return message;
    }

    public String delete(Integer mid){
        String message = "";
        try {
            adminMenuDAO.deleteById(mid);
            message = "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数错误，删除失败";
        }

        return message;
    }

    public String batchDelete(LinkedHashMap menuIds) {
        String message = "";
        for (Object value : menuIds.values()) {
            for (int mid :(List<Integer>)value) {
                message = delete(mid);
                if (!"删除成功".equals(message)) {
                    break;
                }
            }
        }
        return message;
    }

    public String edit(AdminMenu adminMenu){
        String message = "";
        try {
            adminMenuDAO.save(adminMenu);
            message = "修改成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数错误，修改失败";
        }

        return message;
    }

    public List<AdminMenu> search(String keywords) {
        List<AdminMenu> menus =adminMenuDAO.search("%" + keywords + "%");
        handleMenus(menus);
        return menus;
    }

    public List<AdminMenu> all() {
        List<AdminMenu> menus = adminMenuDAO.findAllOrderByParentId();
        handleMenus(menus);
        return menus;
    }
}
