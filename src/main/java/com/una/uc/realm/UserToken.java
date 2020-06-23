package com.una.uc.realm;

import com.una.uc.entity.AdminRole;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.util.List;

/**
 * @author Una
 * @date 2020/3/6 14:18
 * 自定义登录身份
 */
public class UserToken extends UsernamePasswordToken {
    //登录方式
    private LoginType loginType;
    //角色
    private List<AdminRole> roles;

    public UserToken(LoginType loginType, final String username, final String password) {
        super(username, password);
        this.loginType = loginType;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public List<AdminRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AdminRole> roles) {
        this.roles = roles;
    }
}
