package com.una.uc.dao;

import com.una.uc.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Una
 * @date 2020/3/5 19:04
 */
public interface AdminRoleDAO extends JpaRepository<AdminRole, Integer> {
    AdminRole findById(int id);

    AdminRole findByName(String name);

    List<AdminRole> findAllByNameLikeOrNameZhLike(String keyword1, String keyword2);

    @Query(nativeQuery = true, value = "select * from admin_role where enabled = '1' ")
    List<AdminRole> findAllByEnabled();
}
