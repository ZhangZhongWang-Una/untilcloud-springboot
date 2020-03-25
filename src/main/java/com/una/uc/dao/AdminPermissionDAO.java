package com.una.uc.dao;

import com.una.uc.entity.AdminPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Una
 * @date 2020/3/6 21:48
 */
public interface AdminPermissionDAO extends JpaRepository<AdminPermission, Integer> {
    AdminPermission findById(int id);

    void deleteById(Integer id);

    List<AdminPermission> findAllByNameLike(String keyword1);

    List<AdminPermission> findAllByParentId(int parentId);

    @Query(nativeQuery = true, value = "select * from admin_permission order by parent_id ")
    List<AdminPermission> findAllOrderByParentId();
}
