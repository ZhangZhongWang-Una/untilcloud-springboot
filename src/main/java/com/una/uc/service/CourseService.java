package com.una.uc.service;

import com.una.uc.common.Constant;
import com.una.uc.dao.CourseDAO;
import com.una.uc.entity.Course;
import com.una.uc.util.CommonUtil;
import com.una.uc.util.ZXingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author Una
 * @date 2020/5/19 22:59
 */
@Service
public class CourseService {
    @Autowired
    CourseDAO courseDAO;
    @Autowired
    UserService userService;

    public void addOrUpdate(Course course) {
        course.setUpdateTime(new Date());
        courseDAO.save(course);
    }

    public Course getById(int id) {
        return courseDAO.findById(id);
    }

    public String add(Course course, MultipartFile file) {
        String message = "";
        try {
            // 封面
            File imageFolder = new File(Constant.FILE_Photo_Course.string);
            File f = new File(imageFolder, CommonUtil.creatUUID() + file.getOriginalFilename()
                    .substring(file.getOriginalFilename().length() - 4));
            InputStream logo = file.getInputStream();
            file.transferTo(f);
            course.setCover(f.getName());
            // 二维码
            String qrcode = CommonUtil.creatUUID() +  ".jpg";
            String imagePath = Constant.FILE_QrCode.string +  qrcode;
            String content = "http://47.98.142.113:8088/  ";
            ZXingUtil.encodeimage(imagePath, "JPEG", content, 430, 430 , logo);
            String imgURL = Constant.FILE_Url_QrCode.string  + qrcode;
            course.setQrcode(qrcode);

            course.setCreator(userService.getCurrentUserId());
            addOrUpdate(course);
            message = imgURL;
        } catch (Exception e) {
            message = "参数异常，添加失败";
            e.printStackTrace();
        }
        return message;
    }

    public String edit(Course course) {
        String message = "";
        try{
            Course courseInDB = courseDAO.findById(course.getId());
            course.setName(course.getName());
            course.setGrade(course.getGrade());
            course.setSemester(course.getSemester());
            course.setSchool(course.getSchool());
            course.setCollege(course.getCollege());
            course.setMajor(course.getMajor());
            course.setTeacher(course.getTeacher());
            course.setLearnRequire(course.getLearnRequire());
            course.setTeachProgress(course.getTeachProgress());
            course.setExamArrange(course.getExamArrange());
            addOrUpdate(courseInDB);
            message = "修改成功";
        } catch (Exception e) {
            message = "参数异常，修改失败";
            e.printStackTrace();
        }

        return message;
    }

    public String updateCover(MultipartFile file, int cid) throws Exception {
        File imageFolder = new File(Constant.FILE_Photo_Course.string);
        File f = new File(imageFolder, CommonUtil.creatUUID() + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        try {
            file.transferTo(f);
            String imgURL = Constant.FILE_Url_Course.string+ f.getName();

            Course courseInDB = courseDAO.findById(cid);
            courseInDB.setCover(f.getName());
            addOrUpdate(courseInDB);
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "更新失败";
        }
    }

    @Modifying
    @Transactional
    public String delete(int cid) {
        String message = "";
        try {
            courseDAO.deleteById(cid);
            message = "删除成功";
        } catch (Exception e) {
            message = "参数异常，删除失败";
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }

        return message;
    }

    public List<Course> findAllByCreatorId(int uid){
        List<Course> courses = courseDAO.findAllByCreator(uid);
        for (Course course:courses) {
            course.setCover(Constant.FILE_Url_Course.string+course.getCover());
            course.setQrcode(Constant.FILE_Url_QrCode.string+course.getQrcode());
        }
        return courses;
    }

}