package com.una.uc.service;

import com.una.uc.common.Constant;
import com.una.uc.dao.CourseStudentDAO;
import com.una.uc.entity.Course;
import com.una.uc.entity.CourseStudent;
import com.una.uc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Una
 * @date 2020/5/19 23:00
 */
@Service
public class CourseStudentService {
    @Autowired
    CourseStudentDAO courseStudentDAO;
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;

    public void addOrUpdate(CourseStudent courseStudent) {
        courseStudent.setUpdateTime(new Date());
        courseStudentDAO.save(courseStudent);
    }

    public boolean isJoin(int uid, int cid){
        CourseStudent courseStudent = courseStudentDAO.findByCourseAndStu(cid, uid);
        if (null == courseStudent)
            return false;
        else
            return true;
    }

    public String joinCourse(int uid,int cid) {
        String message = "";
        try {
            if (isJoin(cid, uid)){
                return "请勿重复加入课程";
            }
            User user = userService.getById(uid);
            Course course = courseService.getById(cid);
            CourseStudent courseStudent = new CourseStudent();
            courseStudent.setExperience(0);
            courseStudent.setStu(user);
            courseStudent.setCourse(course);

            addOrUpdate(courseStudent);
            message = "加入成功";

        } catch (Exception e) {
            message = "参数异常，加入失败";
            e.printStackTrace();
        }
        return message;
    }

    public List<Map<String,String>> findAllStudentByCourseId(int cid) {
        List<Map<String,String>> temps = courseStudentDAO.findAllStudentByCourseId(cid);
        List<Map<String,String>> maps = new ArrayList<>();
        for (Map temp:temps) {
            Map<String,String> map = new HashMap<>();
            map.putAll(temp);
            map.put("cover",Constant.FILE_Url_User.string + temp.get("cover"));
            maps.add(map);
        }
        return maps;
    }

    public List<Course> findCourseByUserId(int uid){
        List<Course> courses = courseStudentDAO.findCourseByUserId(uid);
        for (Course course:courses) {
            course.setCover(Constant.FILE_Url_Course.string+course.getCover());
            course.setQrcode(Constant.FILE_Url_QrCode.string+course.getQrcode());
        }
        return courses;
    }

    public void addExperience(int cid,int uid){
        CourseStudent courseStudentInDB = courseStudentDAO.findByCourseAndStu(cid, uid);
        int experience = courseStudentInDB.getExperience() + Constant.Sys_Param_Experience.code;
        courseStudentInDB.setExperience(experience);
        addOrUpdate(courseStudentInDB);
    }
}
