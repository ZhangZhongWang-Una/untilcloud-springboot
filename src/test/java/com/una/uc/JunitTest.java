package com.una.uc;

import com.google.zxing.WriterException;
import com.una.uc.common.Constant;
import com.una.uc.entity.SysParam;
import com.una.uc.entity.User;
import com.una.uc.entity.UserInfo;
import com.una.uc.service.SysParamService;
import com.una.uc.service.UserInfoService;
import com.una.uc.service.UserService;
import com.una.uc.util.CommonUtil;
import com.una.uc.util.ZXingUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author Una
 * @date 2020/4/25 0:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JunitTest {
    @Autowired
    UserService userService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    SysParamService sysParamService;

    @Test
    public void test(){
        String userStr = userService.getById(1).getUsername();
        System.out.println(userStr);
    }

    @Test
    public void userInfoDataUpdateTest() {
        System.out.println("---------------- 测试开始 ----------------------");
        List<User> users = userService.list();
        for (User user: users){
            UserInfo userInfo = new UserInfo(user.getUsername(), user);
            userInfoService.addOrUpdate(userInfo);
        }
        System.out.println("---------------- 测试结束 ----------------------");
    }

    @Test
    public void sysParamDataUpdateTest() {
        System.out.println("---------------- 测试开始 ----------------------");
        List<User> users = userService.list();
        for (User user: users){
            SysParam sysParam = new SysParam(new Date(), user);
            sysParamService.addOrUpdate(sysParam);
        }
        System.out.println("---------------- 测试结束 ----------------------");
    }

    @Test
    public void userInfoNameUpdateTest()  {
        System.out.println("---------------- 测试开始 ----------------------");
        List<UserInfo> userInfos = userInfoService.list();
        for (UserInfo userInfo: userInfos){
            userInfo.setName(userInfo.getUser().getName());
            userInfoService.addOrUpdate(userInfo);
        }
        System.out.println("---------------- 测试结束 ----------------------");
    }
}
