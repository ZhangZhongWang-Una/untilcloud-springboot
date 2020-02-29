package com.una.uc.dao;

import com.una.uc.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserDAO extends JpaRepository<User,Integer> {
    // JPA
    User findByUsername(String username);

    User getByUsernameAndPassword(String username,String password);

    // hql
    @Modifying
    @Transactional
    @Query("update User as u set u.password = ?1 where u.username=?2")
    int updatePasswordByUsername(String password,String username);

    // sql
    @Query(nativeQuery = true,value = "select * from user where password = ?2 and username = ?1 ")
    User getByAccountAndPassword(String account,String password);
}
