package com.una.uc.dao;

import com.una.uc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserDAO extends JpaRepository<User,Integer> {
    // JPA
    User findByUsername(String username);

    User findByPhone(String phone);

    User findByEmail(String email);

    User getByUsernameAndPassword(String username,String password);

    // sql
    @Query(nativeQuery = true,value = "select * from user where (username = ?1 || phone = ?1 || email = ?1 )")
    User getByAccount(String account);

    // hql
    @Modifying
    @Transactional
    @Query("update User as u set u.password = ?1 where u.phone=?2")
    int updatePasswordByPhone(String password,String phone);

}
