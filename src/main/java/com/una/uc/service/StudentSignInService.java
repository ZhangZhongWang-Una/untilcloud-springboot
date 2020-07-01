package com.una.uc.service;

import com.una.uc.common.Constant;
import com.una.uc.dao.StudentSignInDAO;
import com.una.uc.entity.CourseSignIn;
import com.una.uc.entity.StudentSignIn;
import com.una.uc.util.CommonUtil;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Una
 * @date 2020/5/19 23:01
 */
@Service
public class StudentSignInService {
    @Autowired
    StudentSignInDAO studentSignInDAO;
    @Autowired
    UserService userService;
    @Autowired
    CourseStudentService courseStudentService;
    @Autowired
    CourseSignInService courseSignInService;

    public void addOrUpdate(StudentSignIn studentSignIn){
        studentSignIn.setStudent(userService.getCurrentUserId());
        studentSignIn.setTime(new Date());
        studentSignInDAO.save(studentSignIn);
    }

    public boolean isSignIn(int cid, int uid){
        StudentSignIn studentSignIn = studentSignInDAO.findByCourseSignInAndStudent(cid, uid);
        if (null == studentSignIn)
            return false;
        else
            return true;
    }

    public String add(StudentSignIn studentSignIn) {
        String message = "";
        int uid = userService.getCurrentUserId();
        try {
            if (isSignIn(studentSignIn.getCourseSignIn().getId(), uid)){
                return "请勿重复签到！";
            }
            if (Constant.SIGNUP_Mode_Time.string.equals(studentSignIn.getMode())) {
                GlobalCoordinates source = new GlobalCoordinates(Double.parseDouble(studentSignIn.getLatitude()), Double.parseDouble(studentSignIn.getLongitude()));
                GlobalCoordinates target = new GlobalCoordinates(Double.parseDouble(studentSignIn.getCourseSignIn().getLatitude()), Double.parseDouble(studentSignIn.getCourseSignIn().getLongitude()));
                double dist = CommonUtil.getDistanceMeter(source, target, Ellipsoid.Sphere);
                if (dist >= Constant.Sys_Param_distance.code){
                    message = "超出签到范围";
                } else if (studentSignIn.getTime().after(studentSignIn.getCourseSignIn().getEndTime())) {
                    message = "超过时间";
                } else {
                    addOrUpdate(studentSignIn);
                    courseStudentService.addExperience(studentSignIn.getCourseSignIn().getCourse().getId(), uid);
                    message = "签到成功";
                }
            } else if (Constant.SIGNUP_Mode_Gesture.string.equals(studentSignIn.getMode())) {
                GlobalCoordinates source = new GlobalCoordinates(Double.parseDouble(studentSignIn.getLatitude()), Double.parseDouble(studentSignIn.getLongitude()));
                GlobalCoordinates target = new GlobalCoordinates(Double.parseDouble(studentSignIn.getCourseSignIn().getLatitude()), Double.parseDouble(studentSignIn.getCourseSignIn().getLongitude()));
                double dist = CommonUtil.getDistanceMeter(source, target, Ellipsoid.Sphere);
                if (dist >= Constant.Sys_Param_distance.code){
                    message = "超出签到范围";
                } else if (!studentSignIn.getValue().equals(studentSignIn.getCourseSignIn().getValue())) {
                    message = "手势错误";
                } else {
                    addOrUpdate(studentSignIn);
                    courseStudentService.addExperience(studentSignIn.getCourseSignIn().getCourse().getId(), uid);
                    message = "签到成功";
                }
            } else {
                message = "参数异常，签到失败";
            }
        } catch (Exception e) {
            message = "参数异常，签到失败";
            e.printStackTrace();
        }
        return message;
    }

    public List<Map<String,Object>> getAllSignInByUserId(int uid, int cid){
        List<StudentSignIn> studentSignIns = studentSignInDAO.findAllByStudentAndCourseId(uid, cid);
        List<CourseSignIn> courseSignIns = courseSignInService.listAllByCourse(cid);
        List<Map<String,Object>> maps = new ArrayList<>();
        for (CourseSignIn courseSignIn: courseSignIns){
            Map<String,Object> map = new HashMap<>();
            map.put("csiid",courseSignIn.getId());
            map.put("time",courseSignIn.getStartTime().toString());
            map.put("mode",courseSignIn.getMode());
            map.put("isSignIn",false);
            for (StudentSignIn studentSignIn: studentSignIns){
                if (courseSignIn.getId() == studentSignIn.getCourseSignIn().getId()){
                    map.put("isSignIn",true);
                    break;
                }
            }
            maps.add(map);

        }
        return maps;
    }
}
