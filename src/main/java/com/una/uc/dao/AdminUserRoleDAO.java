package com.una.uc.dao;

import com.una.uc.entity.AdminUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Una
 * @date 2020/3/5 19:04
 */
public interface AdminUserRoleDAO extends JpaRepository<AdminUserRole, Integer> {
    AdminUserRole findById(int id);

    @Query(nativeQuery = true, value = "select ur.id, ur.uid, ur.rid " +
            "from admin_user_role ur left join admin_role r on r.id = ur.rid " +
            "where r.enabled = '1' and uid = ?1 ")
    List<AdminUserRole> findAllByUid(int Uid);

    void deleteAllByUid(int uid);

    void deleteByRidAndUid(int rid, int uid);

    @Query(nativeQuery = true, value = "select uid from admin_user_role where rid = ?1")
    List<Integer> findAllUidByRid(int rid);
}
