package com.una.uc.service;

import com.una.uc.dao.UserDAO;
import com.una.uc.entity.*;
import com.una.uc.util.CommonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    SysParamService sysParamService;

    public boolean isExist(String username) {
        User user = getByUsername(username);
        return null!=user;
    }

    public User getById(int id) {
        return userDAO.findById(id);
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
        String message = "";
        try{
            String username = user.getUsername();
            String name = user.getName();
            String phone = user.getPhone();
            String email = user.getEmail();
            String password = user.getPassword();

            user.setUsername(username);
            user.setName(name);
            user.setPhone(phone);
            user.setEmail(email);
            user.setEnabled(true);

            if (username.equals("") || password.equals("")) {
                message = "用户名或密码为空，注册失败";
                return message;
            }

            User exist = getByUsername(username);
            if (null != exist) {
                message = "用户名已被注册";
                return message;
            }
            exist = getByPhone(phone);
            if (null != exist) {
                message = "手机号已被注册";
                return message;
            }
            exist = getByEmail(email);
            if (null != exist) {
                message = "邮箱已被注册";
                return message;
            }

            // 默认生成 16 位盐
            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            int times = 2;
            String encodedPassword = new SimpleHash("md5", password, salt, times).toString();

            user.setSalt(salt);
            user.setPassword(encodedPassword);
            userDAO.save(user);

            UserInfo userInfo = new UserInfo(username, user);
            userInfoService.addOrUpdate(userInfo);
            SysParam sysParam = new SysParam(new Date(), user);
            sysParamService.addOrUpdate(sysParam);
            message = "注册成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数异常，注册失败";
        }

        return message;
    }

    public String resetPassword(User user){
        String message = "";
        try{
            String username = user.getUsername();
            String password = user.getPassword();
            if (StringUtils.isEmpty(username)) {
                message = "用户名为空，重置失败";
                return message;
            }
            if (StringUtils.isEmpty(password)) {
                message = "密码为空，重置失败";
                return message;
            }
            User userInDB = userDAO.findByUsername(username);
            if (null == userInDB) {
                message = "未找到该用户，请正确输入用户名";
                return message;
            }
            // 默认生成 16 位盐
            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            int times = 2;
            String encodedPassword = new SimpleHash("md5", password, salt, times).toString();

            userInDB.setSalt(salt);
            userInDB.setPassword(encodedPassword);
            userDAO.save(userInDB);
            message = "重置成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数异常，重置失败";
        }

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

    public List<User> listIsEnabled() {
        List<User> users =  userDAO.findAllByEnabled();
        List<AdminRole> roles;
        for (User user : users) {
            roles = adminRoleService.listRolesByUser(user.getUsername());
            user.setRoles(roles);
        }
        return users;
    }

    public String updateStatus(User user) {
        String message = "";
        try{
            User userInDB = userDAO.findById(user.getId());
            if (null == userInDB) {
                message = "找不到该用户";
                return message;
            }
            userInDB.setEnabled(user.isEnabled());
            userDAO.save(userInDB);
            message =  "更新成功";
        } catch (Exception e){
            e.printStackTrace();
            message = "参数错误，更新失败";
        }

        return message;
    }

    public User getCurrentUser() {
        String account = SecurityUtils.getSubject().getPrincipal().toString();
        User user = getByAccount(account);
        List<AdminRole> roles;
        roles = adminRoleService.listRolesByUser(user.getUsername());
        user.setRoles(roles);
        return user;
    }

    public int getCurrentUserId() {
        String account = SecurityUtils.getSubject().getPrincipal().toString();
        User user = getByAccount(account);
        return user.getId();
    }

    public String editUser(User user) {
        String message = "";
        try {
            User userInDB = userDAO.findByPhone(user.getPhone());
            if (null != userInDB && user.getId() != userInDB.getId()) {
                message = "用户名已被注册，修改失败";
                return message;
            }
            userInDB = userDAO.findByEmail(user.getEmail());
            if (null != userInDB && user.getId() != userInDB.getId()) {
                message = "邮箱已被注册，修改失败";
                return message;
            }
            userInDB = userDAO.findById(user.getId());
            if (null == userInDB) {
                message = "找不到该用户，修改失败";
                return message;
            }
            userInDB.setName(user.getName());
            userInDB.setPhone(user.getPhone());
            userInDB.setEmail(user.getEmail());

            addOrUpdate(userInDB);
            message = adminUserRoleService.saveRoleChanges(userInDB.getId(), user.getRoles());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            message = "参数异常，修改失败";
        }

        return message;
    }

    public List<User> search(String keywords) {
        List<User> us = userDAO.findAllByUsernameLikeOrNameLike('%' + keywords + '%', '%' + keywords + '%');
        List<AdminRole> roles;
        for (User u : us) {
            u.setPassword("");
            u.setSalt("");

            roles = adminRoleService.listRolesByUser(u.getUsername());
            u.setRoles(roles);
        }
        return us;
    }

    public String delete(int uid) {
        String message = "";
        try{
            User u = userDAO.findById(uid);
            if (null == u){
                message = "用户不存在，删除失败";
            } else {
                userDAO.delete(u);
                adminUserRoleService.deleteAllByUid(uid);

                message = "删除成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数错误，删除失败";
        }

        return message;
    }

    public String batchDelete(LinkedHashMap userIds) {
        String message = "";
        for (Object value : userIds.values()) {
            for (int uid :(List<Integer>)value) {
                message = delete(uid);
                if (!"删除成功".equals(message)) {
                    break;
                }
            }
        }
        return message;
    }

    public String registerMobile(User user, String role) {
        String uuid = CommonUtil.creatUUID();
        user.setUsername(uuid);
        String message = register(user);
        if ("注册成功".equals(message)) {
            try {
                User userInDB = getByUsername(uuid);
                AdminUserRole adminUserRole = new AdminUserRole();
                adminUserRole.setUid(userInDB.getId());
                int rid = adminRoleService.findByName(role).getId();
                adminUserRole.setRid(rid);
                adminUserRoleService.addOrUpdate(adminUserRole);
            } catch (Exception e) {
                e.printStackTrace();
                message = "参数异常，注册失败";
            }

        }

        return message;
    }
}
