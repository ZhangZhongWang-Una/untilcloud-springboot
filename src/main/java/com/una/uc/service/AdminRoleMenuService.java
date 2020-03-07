package com.una.uc.service;

import com.una.uc.dao.AdminRoleMenuDAO;
import com.una.uc.entity.AdminRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Action;
import java.util.LinkedHashMap;
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

    public void save(AdminRoleMenu rm) {
        adminRoleMenuDAO.save(rm);
    }

    @Modifying
    @Transactional
    public void updateRoleMenu(int rid, LinkedHashMap menusIds) {
        deleteAllByRid(rid);
        for (Object value : menusIds.values()) {
            for (int mid : (List<Integer>)value) {
                AdminRoleMenu rm = new AdminRoleMenu();
                rm.setRid(rid);
                rm.setMid(mid);
                adminRoleMenuDAO.save(rm);
            }
        }
    }
}
