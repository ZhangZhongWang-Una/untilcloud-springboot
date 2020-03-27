package com.una.uc.service;

import com.una.uc.common.ResultFactory;
import com.una.uc.dao.AdminRoleDAO;
import com.una.uc.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * @author Una
 * @date 2020/3/5 19:15
 */
@Service
public class AdminRoleService {
    @Autowired
    AdminRoleDAO adminRoleDAO;
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminPermissionService adminPermissionService;
    @Autowired
    AdminMenuService adminMenuService;
    @Autowired
    AdminRolePermissionService adminRolePermissionService;
    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    public List<AdminRole> list() {
        List<AdminRole> roles = adminRoleDAO.findAll();
        List<AdminPermission> perms;
        List<AdminMenu> menus;
        for (AdminRole role : roles) {
            perms = adminPermissionService.listPermsByRoleId(role.getId());
            menus = adminMenuService.getMenusByRoleId(role.getId());
            role.setPerms(perms);
            role.setMenus(menus);
        }
        return roles;
    }

    public List<AdminRole> listIsEnabled() {
        return adminRoleDAO.findAllByEnabled();
    }

    public AdminRole findById(int id) {
        return adminRoleDAO.findById(id);
    }

    public AdminRole findByName(String name) {
        return adminRoleDAO.findByName(name);
    }

    public void addOrUpdate(AdminRole adminRole) {
        adminRoleDAO.save(adminRole);
    }

    public List<AdminRole> listRolesByUser(String account) {
        int uid =  userService.getByAccount(account).getId();
        List<AdminRole> roles = new ArrayList<>();
        List<AdminUserRole> urs = adminUserRoleService.listAllByUid(uid);
        for (AdminUserRole ur: urs) {
            AdminRole role = adminRoleDAO.findById(ur.getRid());
            if (true == role.isEnabled())
                roles.add(role);
        }
        return roles;
    }

    public String delete(int id){
        String message = "";
        try{
            AdminRole role = adminRoleDAO.findById(id);
            if (null == role){
                message = "角色不存在，删除失败";
            } else {
                adminRoleDAO.delete(role);
                adminRolePermissionService.deleteAllByRid(id);
                adminRoleMenuService.deleteAllByRid(id);
                message = "删除成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数错误，删除失败";
        }

        return message;
    }

    public String batchDelete(LinkedHashMap roleIds) {
        String message = "";
        for (Object value : roleIds.values()) {
            for (int rid :(List<Integer>)value) {
                message = delete(rid);
                if (!"删除成功".equals(message)) {
                    break;
                }
            }
        }
        return message;
    }

    public String edit(AdminRole requestRole){
        String message = "";
        try {
            List<AdminMenu> menus = requestRole.getMenus();
            List<AdminPermission> perms = requestRole.getPerms();
            AdminRole adminRoleInDB = findById(requestRole.getId());
            if (null == adminRoleInDB) {
                message = "该角色不存在";
                return message;
            }
            adminRoleInDB = findByName(requestRole.getName());
            if (adminRoleInDB == null) {
                addOrUpdate(requestRole);
                message = editRoleMenuAndPerm(requestRole.getId(), menus, perms);
            } else if (adminRoleInDB.getName().equals(requestRole.getName()) && adminRoleInDB.getId() != requestRole.getId()){
                message = "角色" + adminRoleInDB.getName() + "已存在，修改失败";
            } else {
                addOrUpdate(requestRole);
                message = editRoleMenuAndPerm(requestRole.getId(), menus, perms);
            }
        } catch (Exception e){
            e.printStackTrace();
            message = "参数错误，修改失败";
        }

        return message;
    }

    public String editRoleMenuAndPerm(int rid,List<AdminMenu> menus, List<AdminPermission>perms) {
        String message = adminRoleMenuService.updateRoleMenu(rid, menus);
        if (!"更新成功".equals(message))
            return message;
        message = adminRolePermissionService.updateRolePerm(rid, perms);
        return message;
    }
    public String updateRoleStatus(AdminRole requestRole){
        String message = "";
        try{
            AdminRole adminRoleInDB = findById(requestRole.getId());
            if (null == adminRoleInDB) {
                message = "该角色不存在";

            } else {
                adminRoleInDB.setEnabled(requestRole.isEnabled());
                addOrUpdate(adminRoleInDB);
                message = "更新成功";
            }
        } catch (Exception e){
            e.printStackTrace();
            message = "参数错误，修改失败";
        }

        return message;
    }

    public List<AdminRole> search(String keywords) {
        return adminRoleDAO.findAllByNameLikeOrNameZhLike('%' + keywords + '%', '%' + keywords + '%');
    }
}
