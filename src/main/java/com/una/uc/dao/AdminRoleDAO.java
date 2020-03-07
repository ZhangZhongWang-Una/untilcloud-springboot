package com.una.uc.dao;

import com.una.uc.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Una
 * @date 2020/3/5 19:04
 */
public interface AdminRoleDAO extends JpaRepository<AdminRole, Integer> {
    AdminRole findById(int id);
}
