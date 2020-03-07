package com.una.uc.dao;

import com.una.uc.entity.AdminPermission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Una
 * @date 2020/3/6 21:48
 */
public interface AdminPermissionDAO extends JpaRepository<AdminPermission, Integer> {
    AdminPermission findById(int id);
}
