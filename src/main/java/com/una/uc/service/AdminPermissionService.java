package com.una.uc.service;

import com.una.uc.dao.AdminPermissionDAO;
import com.una.uc.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Una
 * @date 2020/3/6 21:50
 */
@Service
public class AdminPermissionService {
    @Autowired
    AdminPermissionDAO adminPermissionDAO;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    AdminRolePermissionService adminRolePermissionService;
    @Autowired
    UserService userService;

    public AdminPermission findById(int id) {
        return adminPermissionDAO.findById(id);
    }

    public List<AdminPermission> list() {
        List<AdminPermission> ps = adminPermissionDAO.findAll();
        return handlePerms(ps);

    }

    public List<AdminPermission> getAllByParentId(int parentId) {
        return adminPermissionDAO.findAllByParentId(parentId);}

//    public void handlePerms(List<AdminPermission>perms) {
//        for (AdminPermission perm : perms) {
//            perm.setChildren(getAllByParentId(perm.getId()));
//        }
//
//        Iterator<AdminPermission> iterator = perms.iterator();
//        while (iterator.hasNext()) {
//            AdminPermission perm = iterator.next();
//            if (perm.getParentId() != 0) {
//                iterator.remove();
//            }
//        }
//    }

    public List<AdminPermission> handlePerms(List<AdminPermission>perms) {
        List<AdminPermission> deletePerms = new ArrayList<>();
        for (AdminPermission perm: perms){
            for (AdminPermission perm2: perms) {
                if (perm.getId() == perm2.getParentId()) {
                    if (null == perm.getChildren()) {
                        List<AdminPermission> children = new ArrayList<>();
                        children.add(perm2);
                        perm.setChildren(children);
                        deletePerms.add(perm2);
                    } else {
                        perm.getChildren().add(perm2);
                        deletePerms.add(perm2);
                    }
                }
            }
        }
        for (AdminPermission perm: deletePerms) {
            perms.remove(perm);
        }
        return perms;
    }

    public boolean needFilter(String requestAPI) {
        List<AdminPermission> ps = adminPermissionDAO.findAll();
        for (AdminPermission p: ps) {
            // 这里我们进行前缀匹配，拥有父权限就拥有所有子权限
            if (requestAPI.startsWith(p.getUrl())) {
                return true;
            }
        }
        return false;
    }

    public List<AdminPermission> listPermsByRoleId(int rid) {
        List<AdminRolePermission> rps = adminRolePermissionService.findAllByRid(rid);
        List<AdminPermission> perms = new ArrayList<>();
        for (AdminRolePermission rp : rps) {
            AdminPermission perm =adminPermissionDAO.findById(rp.getPid());
            perm.setChildren(null);
            perms.add(perm);
        }
        return handlePerms(perms);
    }

    public Set<String> listPermissionURLsByUser(String account) {
        List<AdminRole> roles = adminRoleService.listRolesByUser(account);
        Set<String> URLs = new HashSet<>();

        for (AdminRole role : roles) {
            List<AdminRolePermission> rps = adminRolePermissionService.findAllByRid(role.getId());
            for (AdminRolePermission rp : rps) {
                URLs.add(adminPermissionDAO.findById(rp.getPid()).getUrl());
            }
        }
        return URLs;
    }

    public List<AdminPermission> search(String keywords) {
        return adminPermissionDAO.search("%" + keywords + "%");
    }

    public String add(AdminPermission adminPermission){
        String message = "";
        try {
            adminPermissionDAO.save(adminPermission);
            message = "添加成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数错误，添加失败";
        }

        return message;
    }

    public String delete(Integer pid){
        String message = "";
        try {
            adminPermissionDAO.deleteById(pid);
            adminRolePermissionService.deleteAllByPid(pid);
            message = "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数错误，删除失败";
        }

        return message;
    }

    public String batchDelete(LinkedHashMap permIds) {
        String message = "";
        for (Object value : permIds.values()) {
            for (int pid :(List<Integer>)value) {
                message = delete(pid);
                if (!"删除成功".equals(message)) {
                    break;
                }
            }
        }
        return message;
    }

    public String edit(AdminPermission adminPermission){
        String message = "";
        try {
            adminPermissionDAO.save(adminPermission);
            message = "修改成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数错误，修改失败";
        }

        return message;
    }

    public List<AdminPermission> all() {
        return adminPermissionDAO.findAllOrderByParentId();
    }
}
