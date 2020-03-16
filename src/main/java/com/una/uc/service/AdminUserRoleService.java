package com.una.uc.service;

import com.una.uc.dao.AdminUserRoleDAO;
import com.una.uc.entity.AdminRole;
import com.una.uc.entity.AdminUserRole;
import com.una.uc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * @author Una
 * @date 2020/3/5 19:16
 */
@Service
public class AdminUserRoleService {
    @Autowired
    AdminUserRoleDAO adminUserRoleDAO;

    public List<AdminUserRole> listAllByUid(int uid) {
        return adminUserRoleDAO.findAllByUid(uid);
    }

    public void addOrUpdate(AdminUserRole adminUserRole) {
        adminUserRoleDAO.save(adminUserRole);
    }
    @Transactional
    public void saveRoleChanges(int uid, List<AdminRole> roles) {
        adminUserRoleDAO.deleteAllByUid(uid);
        for (AdminRole role : roles) {
            AdminUserRole ur = new AdminUserRole();
            ur.setUid(uid);
            ur.setRid(role.getId());
            adminUserRoleDAO.save(ur);
        }
    }

    public void addUserRole(int uid, int rid){
        AdminUserRole ur = new AdminUserRole();
        ur.setRid(uid);
        ur.setRid(rid);
        adminUserRoleDAO.save(ur);
    }

    public void deleteByRidAndUid(int rid, int uid){
        adminUserRoleDAO.deleteByRidAndUid(rid, uid);
    }

    public List<Integer> findAllUidByRid(int rid) {
        return adminUserRoleDAO.findAllUidByRid(rid);
    }

    @Modifying
    @Transactional
    public String assistUser(int rid, LinkedHashMap userIds) {
        String message = "";
        try{
            Set<Integer> userIdsSetInDB = new HashSet<>(findAllUidByRid(rid));
            Set<Integer> temp = new HashSet<>();
            for (Integer uid : userIdsSetInDB){
                temp.add(uid);
            }
            Set<Integer> userIdsSet = null;
            for (Object value : userIds.values()) {
                List<Integer> userIdsList = (List<Integer>)value;
                userIdsSet = new HashSet<>(userIdsList);
                for (int i = 0; i < userIdsList.size(); i++) {
                    // 去重请求的用户和数据库的用户 剩下的为新增的和需要减少的 重复的数据不变
                    if (!temp.add(userIdsList.get(i))){
                        // 已经存在
                        userIdsSetInDB.remove(userIdsList.get(i));
                        userIdsSet.remove(userIdsList.get(i));
                    }
                }
            }
            if (!userIdsSet.isEmpty()) {
                for (Integer uid : userIdsSet) {
                    AdminUserRole ur = new AdminUserRole();
                    ur.setRid(rid);
                    ur.setUid(uid);
                    addOrUpdate(ur);
                }
            }
            if (!userIdsSetInDB.isEmpty()) {
                for (Integer uid : userIdsSetInDB){
                    deleteByRidAndUid(rid, uid);
                }
            }
            message = "分配成功";
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            message = "参数错误，分配失败";
        }

        return message;
    }

    @Modifying
    @Transactional
    public void deleteByUid(int uid) {
        adminUserRoleDAO.deleteAllByUid(uid);
    }
}
