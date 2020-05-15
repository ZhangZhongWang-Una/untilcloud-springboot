package com.una.uc.service;

import com.una.uc.common.Constant;
import com.una.uc.common.ResultFactory;
import com.una.uc.dao.UserInfoDAO;
import com.una.uc.entity.User;
import com.una.uc.entity.UserInfo;
import com.una.uc.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Una
 * @date 2020/4/24 23:54
 */
@Service
public class UserInfoService {
    @Autowired
    UserInfoDAO userInfoDAO;
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;

    public void addOrUpdate(UserInfo userInfo) {
        userInfoDAO.save(userInfo);
    }

    public UserInfo getById(int id) {
        return userInfoDAO.findById(id);
    }

    public UserInfo getByCurrentUser() {
        User loginUser = userService.getCurrentUser();
        UserInfo loginUserInfo = userInfoDAO.findByUserId(loginUser.getId());
        loginUserInfo.setName(loginUser.getName());
        loginUserInfo.setRoles(loginUser.getRoles());
        loginUserInfo.setCover(Constant.FILE_Photo_Base_Url.string + loginUserInfo.getCover());

        return loginUserInfo;
    }

    public String edit(UserInfo userInfo) {
        String message = "";
        try {
            UserInfo userInfoInDB = userInfoDAO.findById(userInfo.getId());
            if (null == userInfoInDB) {
                message = "找不到该用户信息，修改失败";
            } else {
                User user = userInfoInDB.getUser();
                user.setName(userInfo.getName());
                userService.addOrUpdate(user);

                userInfoInDB.setNickname(userInfo.getNickname());
                userInfoInDB.setIno(userInfo.getIno());
                userInfoInDB.setSex(userInfo.getSex());
                userInfoInDB.setSchool(userInfo.getSchool());
                userInfoInDB.setCollege(userInfo.getCollege());
                userInfoInDB.setMajor(userInfo.getMajor());
                addOrUpdate(userInfoInDB);

                //修改角色
                message = adminUserRoleService.saveRoleChanges(user.getId(), userInfo.getRoles());

            }
        } catch (Exception e){
            message = "参数错误，修改失败";
            e.printStackTrace();
        }

        return message;
    }

    public String updateCover(MultipartFile file) throws Exception {
        File imageFolder = new File(Constant.FILE_Photo_Path.string);
        File f = new File(imageFolder, CommonUtil.creatUUID() + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = Constant.FILE_Photo_Base_Url.string + f.getName();

            int uid = userService.getCurrentUser().getId();
            UserInfo userInfoInDB = userInfoDAO.findAllByUserId(uid);
            userInfoInDB.setCover(f.getName());
            addOrUpdate(userInfoInDB);
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "更新失败";
        }
    }
}
