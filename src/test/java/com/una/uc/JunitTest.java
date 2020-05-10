package com.una.uc;

import com.una.uc.entity.User;
import com.una.uc.entity.UserInfo;
import com.una.uc.service.UserInfoService;
import com.una.uc.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void test(){
        String userStr = userService.getById(1).getUsername();
        System.out.println(userStr);
    }

    @Test
    public void userInfoTest() {
        System.out.println("---------------- 测试开始 ----------------------");
        List<User> users = userService.list();
        for (User user: users){
            UserInfo userInfo = new UserInfo(user.getUsername(), user.getPhone(), user.getName());
            userInfoService.addOrUpdate(userInfo);
        }
        System.out.println("---------------- 测试结束 ----------------------");
    }
}
