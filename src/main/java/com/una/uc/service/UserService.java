package com.una.uc.service;

import com.una.uc.dao.UserDAO;
import com.una.uc.entity.AdminRole;
import com.una.uc.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    AdminRoleService adminRoleService;

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

    public void addOrUpdate(User user) {
        userDAO.save(user);
    }

    public String register(User user) {
        String username = user.getUsername();
        String nickname = user.getNickname();
        String phone = user.getPhone();
        String email = user.getEmail();
        String password = user.getPassword();

        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);
        nickname = HtmlUtils.htmlEscape(nickname);
        user.setNickname(nickname);
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

    public String resetPassword(User user){
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isEmpty(username)) {
            String message = "用户名为空，重置失败";
            return message;
        }
        if (StringUtils.isEmpty(password)) {
            String message = "密码为空，重置失败";
            return message;
        }
        User userInDB = userDAO.findByUsername(username);
        if (null == userInDB) {
            String message = "未找到该用户，请正确输入用户名";
            return message;
        }
        // 默认生成 16 位盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();

        userInDB.setSalt(salt);
        userInDB.setPassword(encodedPassword);
        userDAO.save(userInDB);
        String message = "重置成功";

        return message;
    }

    public List<User> list() {
        List<User> users =  userDAO.list();
        List<AdminRole> roles;
        for (User user : users) {
            roles = adminRoleService.listRolesByUser(user.getUsername());
            user.setRoles(roles);
        }
        return users;
    }

    public String updateStatus(User user) {
        String username = HtmlUtils.htmlEscape(user.getUsername());
        if (StringUtils.isEmpty(username)) {
            String message= "用户名不能为空";
            return message;
        }
        User userInDB = userDAO.findByUsername(username);
        if (null == userInDB) {
            String message = "找不到该用户";
            return message;
        }
        userInDB.setEnabled(user.isEnabled());
        userDAO.save(userInDB);
        return "更新成功";
    }

    public User getCurrentUser() {
        String account = SecurityUtils.getSubject().getPrincipal().toString();
        User user = getByAccount(account);
//        List<AdminRole> roles;
//        roles = adminRoleService.listRolesByUser(user.getUsername());
//        user.setRoles(roles);
        return user;
    }

}
