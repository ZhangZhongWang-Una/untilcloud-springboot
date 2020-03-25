package com.una.uc.dao;

import com.una.uc.entity.AdminMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Una
 * @date 2020/3/5 19:04
 */
public interface AdminMenuDAO extends JpaRepository<AdminMenu,Integer> {
    AdminMenu findById(int id);

    List<AdminMenu> findAllByParentId(int parentId);

    List<AdminMenu> findAllByNameZhLike(String keyword1);

    void deleteById(Integer id);

    @Query(nativeQuery = true, value = "select * from admin_menu order by parent_id ")
    List<AdminMenu> findAllOrderByParentId();
}
