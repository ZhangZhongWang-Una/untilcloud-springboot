package com.una.uc.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author Una
 * @date 2020/3/6 14:18
 * 自定义登录身份
 */
public class UserToken extends UsernamePasswordToken {
    //登录方式
    private LoginType loginType;

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
}
