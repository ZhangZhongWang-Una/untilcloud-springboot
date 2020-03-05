package com.una.uc.service;

import com.una.uc.dao.UserDAO;
import com.una.uc.entity.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public boolean isExist(String username) {
        User user = getByUsername(username);
        return null!=user;
    }

    public User getByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public User getByPhone(String phone) {
        return userDAO.findByPhone(phone);
    }

    public User getByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public User getByAccount(String account) {
        return userDAO.getByAccount(account);
    }

    public User get(String username, String password){
        return userDAO.getByUsernameAndPassword(username, password);
    }

    public void add(User user) {
        userDAO.save(user);
    }

    public String register(User user) {
        String username = user.getUsername();
        String role = user.getRole();
        String phone = user.getPhone();
        String email = user.getEmail();
        String password = user.getPassword();

        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);
        role = HtmlUtils.htmlEscape(role);
        user.setRole(role);
        phone = HtmlUtils.htmlEscape(phone);
        user.setPhone(phone);
        email = HtmlUtils.htmlEscape(email);
        user.setEmail(email);
        user.setEnabled(true);

        if (username.equals("") || password.equals("")) {
            String message = "用户名或密码为空，注册失败";
            return message;
        }

        User exist = getByUsername(username);
        if (null != exist) {
            String message = "用户名已被注册";
            return message;
        }
        exist = getByPhone(phone);
        if (null != exist) {
            String message = "手机号已被注册";
            return message;
        }
        exist = getByEmail(email);
        if (null != exist) {
            String message = "邮箱已被注册";
            return message;
        }

        // 默认生成 16 位盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();

        user.setSalt(salt);
        user.setPassword(encodedPassword);
        userDAO.save(user);
        String message = "注册成功";

        return message;
    }


}
