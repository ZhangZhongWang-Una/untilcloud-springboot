package com.una.uc.service;

import com.una.uc.dao.UserInfoDAO;
import com.una.uc.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Una
 * @date 2020/4/24 23:54
 */
@Service
public class UserInfoService {
    @Autowired
    UserInfoDAO userInfoDAO;

    public void addOrUpdate(UserInfo userInfo) {
        userInfoDAO.save(userInfo);
    }
}
