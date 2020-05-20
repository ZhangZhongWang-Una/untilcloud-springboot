package com.una.uc.service;

import com.una.uc.dao.AdminRolePermissionDAO;
import com.una.uc.entity.AdminPermission;
import com.una.uc.entity.AdminRoleMenu;
import com.una.uc.entity.AdminRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Una
 * @date 2020/3/6 21:51
 */
@Service
public class AdminRolePermissionService {
    @Autowired
    AdminRolePermissionDAO adminRolePermissionDAO;

    List<AdminRolePermission> findAllByRid(int rid) {
        return adminRolePermissionDAO.findAllByRid(rid);
    }

//    @Modifying
    @Transactional
    public void savePermChanges(int rid, List<AdminPermission> perms) {
        adminRolePermissionDAO.deleteAllByRid(rid);
        for (AdminPermission perm : perms) {
            AdminRolePermission rp = new AdminRolePermission();
            rp.setRid(rid);
            rp.setPid(perm.getId());
            adminRolePermissionDAO.save(rp);
        }
    }

    @Modifying
    @Transactional
    public void deleteAllByRid(int rid){
        adminRolePermissionDAO.deleteAllByRid(rid);
    }

    @Modifying
    @Transactional
    public void deleteAllByPid(int pid){
        adminRolePermissionDAO.deleteAllByPid(pid);
    }

    @Modifying
    @Transactional
    public String updateRolePerm(int rid, List<AdminPermission> perms) {
        String message = "";
        try{
            deleteAllByRid(rid);
            for (AdminPermission perm: perms){
                AdminRolePermission rp = new AdminRolePermission();
                rp.setRid(rid);
                rp.setPid(perm.getId());
                adminRolePermissionDAO.save(rp);
            }
            message = "更新成功";
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            message = "参数错误，更新失败";
        }

        return message;
    }

//    @Transactional
//    public String updateRolePerms(int rid, LinkedHashMap permIds) {
//        String message = "";
//        try{
//            deleteAllByRid(rid);
//            for (Object value : permIds.values()) {
//                for (int pid : (List<Integer>)value) {
//                    AdminRolePermission rp = new AdminRolePermission();
//                    rp.setRid(rid);
//                    rp.setPid(pid);
//                    adminRolePermissionDAO.save(rp);
//                }
//            }
//            message = "更新成功";
//        } catch (Exception e) {
//            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            message = "参数错误，更新失败";
//        }
//
//        return message;
//
//    }
}
