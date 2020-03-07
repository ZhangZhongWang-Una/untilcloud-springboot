package com.una.uc.dao;

import com.una.uc.entity.AdminUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Una
 * @date 2020/3/5 19:04
 */
public interface AdminUserRoleDAO extends JpaRepository<AdminUserRole, Integer> {
    AdminUserRole findById(int id);

    List<AdminUserRole> findAllByUid(int Uid);

    void deleteAllByUid(int uid);
}
