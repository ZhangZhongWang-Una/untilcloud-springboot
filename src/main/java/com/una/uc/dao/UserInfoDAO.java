package com.una.uc.dao;

import com.una.uc.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Una
 * @date 2020/4/24 23:53
 */
public interface UserInfoDAO extends JpaRepository<UserInfo,Integer> {

}
