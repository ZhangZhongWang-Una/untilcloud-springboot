package com.una.uc.dao;

import com.una.uc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDAO extends JpaRepository<User,Integer> {
    // JPA
    User findById(int id);

    User findByUsername(String username);

    User findByPhone(String phone);

    User findByEmail(String email);

    // sql
    @Query(nativeQuery = true,value = "select * from user where (username = ?1 || phone = ?1 || email = ?1 )")
    User getByAccount(String account);

    @Query(value = "select new User(u.id,u.username,u.name,u.phone,u.email,u.enabled) from User u")
    List<User> list();

    @Query(value = "select new User(u.id,u.username,u.name,u.phone,u.email,u.enabled) from User u where u.enabled = true")
    List<User> findAllByEnabled();

    // hql
    @Modifying
    @Transactional
    @Query("update User as u set u.password = ?2 , u.salt = ?3 where u.phone=?1")
    int updatePasswordAndSaltByPhone(String phone, String encodedPassword, String salt);

    List<User> findAllByUsernameLikeOrNameLike(String keyword1, String keyword2);
}
