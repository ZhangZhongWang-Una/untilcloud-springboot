package com.una.uc.service;

import com.una.uc.dao.AdminRoleMenuDAO;
import com.una.uc.entity.AdminMenu;
import com.una.uc.entity.AdminRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * @author Una
 * @date 2020/3/5 19:16
 */
@Service
public class AdminRoleMenuService {
    @Autowired
    AdminRoleMenuDAO adminRoleMenuDAO;

    public List<AdminRoleMenu> findAllByRid(int rid) {
        return adminRoleMenuDAO.findAllByRid(rid);
    }

    @Modifying
    @Transactional
    public void deleteAllByRid(int rid) {
        adminRoleMenuDAO.deleteAllByRid(rid);
    }

    @Modifying
    @Transactional
    public void deleteAllByMid(int mid) {
        adminRoleMenuDAO.deleteAllByMid(mid);
    }

    public void save(AdminRoleMenu rm) {
        adminRoleMenuDAO.save(rm);
    }

    @Modifying
    @Transactional
    public String updateRoleMenu(int rid, List<AdminMenu> menus) {
        String message = "";
        try{
            deleteAllByRid(rid);
            for (AdminMenu menu: menus){
                AdminRoleMenu rm = new AdminRoleMenu();
                rm.setRid(rid);
                rm.setMid(menu.getId());
                adminRoleMenuDAO.save(rm);
            }
            message = "更新成功";
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            message = "参数错误，更新失败";
        }

        return message;
    }

//    @Modifying
//    @Transactional
//    public String updateRoleMenu(int rid, LinkedHashMap menusIds) {
//        String message = "";
//        try{
//            deleteAllByRid(rid);
//            for (Object value : menusIds.values()) {
//                for (int mid : (List<Integer>)value) {
//                    AdminRoleMenu rm = new AdminRoleMenu();
//                    rm.setRid(rid);
//                    rm.setMid(mid);
//                    adminRoleMenuDAO.save(rm);
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
//    }
}
