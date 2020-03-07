package com.una.uc.dao;

import com.una.uc.entity.AdminRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * @author Una
 * @date 2020/3/5 19:04
 */
public interface AdminRoleMenuDAO extends JpaRepository<AdminRoleMenu,Integer> {
    AdminRoleMenu findById(int id);

    List<AdminRoleMenu> findAllByRid(int rid);

    void deleteAllByRid(int rid);
}
