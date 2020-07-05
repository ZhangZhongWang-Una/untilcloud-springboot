package com.una.uc.dao;


import com.una.uc.entity.SysParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author Una
 * @date 2020/5/10 14:58
 */
public interface SysParamDAO extends JpaRepository<SysParam,Integer> {
    SysParam findById(int id);

    // List<SysParam> findAllByKey1Like(String keyword1);

    @Query(nativeQuery = true, value = "select * from sys_param sp " +
            " left join user u on u.id = sp.user_id where u.name like ?1 or u.username like?1 ")
    List<SysParam> search(String keyword1);

    @Query(nativeQuery = true, value = "select * from sys_param  " +
            " where user_id = ?1 ")
    SysParam findByUserId(int uid);
}
