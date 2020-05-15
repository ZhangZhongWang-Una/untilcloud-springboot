package com.una.uc.dao;

import com.una.uc.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Una
 * @date 2020/4/24 23:53
 */
public interface UserInfoDAO extends JpaRepository<UserInfo,Integer> {
    UserInfo findById(int id);

    @Query("select new UserInfo(i.id, i.username, i.nickname, i.ino, " +
            " i.sex, i.school, i.college, i.cover) from UserInfo i where i.user.id = ?1 ")
    UserInfo findByUserId(int id);

    @Query("from UserInfo i where i.user.id = ?1 ")
    UserInfo findAllByUserId(int id);

}
