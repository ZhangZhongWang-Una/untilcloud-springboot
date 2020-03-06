package com.una.uc.realm;

import com.una.uc.common.Constant;
import com.una.uc.entity.User;
import com.una.uc.service.UserService;
import com.una.uc.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @author Una
 * @date 2020/3/6 14:25
 * 手机验证码登录realm
 */
@Slf4j
public class UserPhoneRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public String getName() {
        return LoginType.USER_PHONE.getType();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof UserToken) {
            return ((UserToken) token).getLoginType() == LoginType.USER_PHONE;
        } else {
            return false;
        }
    }

    @Override
    public void setAuthorizationCacheName(String authorizationCacheName) {
        super.setAuthorizationCacheName(authorizationCacheName);
    }

    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        log.info("---------------- 手机验证码登录 ----------------------");
        UserToken token = (UserToken) authcToken;
        String phone = token.getUsername();
        // 手机验证码
        String requestVerificationCode = String.valueOf(token.getPassword());
        Object redisVerificationCode = redisUtil.get(phone + Constant.SMS_Verification_Code.code);
        if (ObjectUtils.isEmpty(redisVerificationCode)) {
            log.debug("请重新获取验证码，手机号为：{}", phone);
            throw new IncorrectCredentialsException();
        } else if (!redisVerificationCode.toString().equals(requestVerificationCode)) {
            log.debug("验证码错误，手机号为：{}", phone);
            throw new IncorrectCredentialsException();
        }

        User user = userService.getByPhone(phone);
        if(user == null){
            throw new UnknownAccountException();
        }
        // 用户为禁用状态
        if(!user.isEnabled()){
            throw new DisabledAccountException();
        }
        // 完成登录，此处已经不需要做密码校验
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(), //用户
                requestVerificationCode, //密码
                getName()  //realm name
        );
        return authenticationInfo;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

}
