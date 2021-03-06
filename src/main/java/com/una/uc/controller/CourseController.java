package com.una.uc.controller;


import com.una.uc.common.Constant;
import com.una.uc.common.Result;
import com.una.uc.common.ResultFactory;
import com.una.uc.entity.Course;

import com.una.uc.entity.CourseSignIn;
import com.una.uc.entity.StudentSignIn;
import com.una.uc.entity.SysParam;
import com.una.uc.service.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Una
 * @date 2020/5/22 20:08
 */
@Slf4j
@RestController
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    CourseStudentService courseStudentService;
    @Autowired
    UserService userService;
    @Autowired
    CourseSignInService courseSignInService;
    @Autowired
    StudentSignInService studentSignInService;


    /** -------------------------- 课程表 -------------------------------------- **/


    @PostMapping("/api/class/course/add")
    public Result addCourse(HttpServletRequest request) {
        log.info("---------------- 添加课程 ----------------------");
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("cover");
        Course course = new Course();
        course.setName(params.getParameter("name"));
        course.setGrade(params.getParameter("grade"));
        course.setSemester(params.getParameter("semester"));
        course.setSchool(params.getParameter("school"));
        course.setCollege(params.getParameter("college"));
        course.setMajor(params.getParameter("major"));
        course.setTeacher(params.getParameter("teacher"));
        course.setLearnRequire(params.getParameter("learnRequire"));
        course.setTeachProgress(params.getParameter("teachProgress"));
        course.setExamArrange(params.getParameter("examArrange"));
        course.setSchoolId(Integer.parseInt(params.getParameter("schoolId")));
        course.setCollegeId(Integer.parseInt(params.getParameter("collegeId")));

        String message = courseService.add(course, files.get(0));
        if ("参数异常，添加失败".equals(message))
            return ResultFactory.buildFailResult(message);
        else
            return ResultFactory.buildSuccessResult(message);
    }

    @PostMapping("/api/class/course/modify")
    public Result editCourseContainCover(HttpServletRequest request) {
        log.info("---------------- 修改课程包含头像 ----------------------");
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("cover");
        Course course = new Course();
        course.setId(Integer.parseInt(params.getParameter("id")));
        course.setName(params.getParameter("name"));
        course.setGrade(params.getParameter("grade"));
        course.setSemester(params.getParameter("semester"));
        course.setSchool(params.getParameter("school"));
        course.setCollege(params.getParameter("college"));
        course.setMajor(params.getParameter("major"));
        course.setTeacher(params.getParameter("teacher"));
        course.setLearnRequire(params.getParameter("learnRequire"));
        course.setTeachProgress(params.getParameter("teachProgress"));
        course.setExamArrange(params.getParameter("examArrange"));
        String message = "";
        if(0 == files.size()) {
            message = courseService.editContainCover(course, null);
        } else {
            message = courseService.editContainCover(course, files.get(0));
        }
        if ("参数异常，修改失败".equals(message))
            return ResultFactory.buildFailResult(message);
        else
            return ResultFactory.buildSuccessResult(message);
    }

    @GetMapping("/api/class/course/delete")
    public Result deleteCourse(@RequestParam int cid) {
        log.info("---------------- 删除课程 ----------------------");
        String message = courseService.delete(cid);
        if ("删除成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @PostMapping("/api/class/course/delete")
    public Result batchDeleteCourse(@RequestBody LinkedHashMap courseIds) {
        log.info("---------------- 批量删除课程 ----------------------");
        String message = courseService.batchDelete(courseIds);
        if ("删除成功".equals(message)) {
            return ResultFactory.buildSuccessResult(message);
        } else {
            return ResultFactory.buildFailResult(message);
        }
    }

    @PutMapping("/api/class/course/edit")
    public Result eidtCourse(@RequestBody Course course) {
        log.info("---------------- 修改课程 ----------------------");
        String message = courseService.edit(course);
        if ("修改成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @PostMapping("/api/class/course/cover")
    public Result updateCourseCover(HttpServletRequest request) throws Exception {
        log.info("---------------- 修改课程封面 ----------------------");
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        int cid = Integer.parseInt(params.getParameter("id"));
        String message = courseService.updateCover(files.get(0), cid);
        if (!"更新失败".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @GetMapping("/api/class/course/getCreate")
    public Result getCurrentUserCreateCourse() {
        log.info("---------------- 获取我创建的课程 ----------------------");
        List<Course> courses= courseService.findAllByCreatorId(userService.getCurrentUserId());
        return ResultFactory.buildSuccessResult(courses);
    }

    @GetMapping("/api/class/course/search")
    public Result searchCourse(@RequestParam String keywords) {
        log.info("---------------- 搜索课程 ----------------------");
        List<Course> courses= courseService.search(keywords);
        return ResultFactory.buildSuccessResult(courses);
    }

    @GetMapping("/api/class/course/all")
    public Result getAllCourse() {
        log.info("---------------- 获取所有课程 ----------------------");
        List<Course> courses= courseService.findAll();
        return ResultFactory.buildSuccessResult(courses);
    }

    @GetMapping("/api/class/stu/course/get/{cid}")
    public Result getById(@PathVariable("cid") int cid) {
        log.info("---------------- 获取单一课程 ----------------------");
        Course course= courseService.findById(cid);
        if (null != course)
            return ResultFactory.buildSuccessResult(course);
        else
            return ResultFactory.buildFailResult("请输入正确课程Id");
    }

    /** -------------------------- 课程学生表 -------------------------------------- **/


    @GetMapping("/api/class/stu/course/stu")
    public Result getStudentsByCourseId(@RequestParam int cid) {
        log.info("---------------- 获取课程学生 ----------------------");
        List<Map<String,String>> courseStus = courseStudentService.findAllStudentByCourseId(cid);
        return ResultFactory.buildSuccessResult(courseStus);
    }

    @GetMapping("/api/class/stu/course/join")
    public Result joinCourse(@RequestParam int cid) {
        log.info("---------------- 加入课程 ----------------------");
        String message = courseStudentService.joinCourse(userService.getCurrentUserId(), cid);
        if ("加入成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @GetMapping("/api/class/stu/course/getJoin")
    public Result getCurrentUserJoinCourse() {
        log.info("---------------- 获取我加入的课程 ----------------------");
        List<Course> courses= courseStudentService.findCourseByUserId(userService.getCurrentUserId());
        return ResultFactory.buildSuccessResult(courses);
    }


    /** -------------------------- 课程签到表 -------------------------------------- **/


    @PostMapping("/api/class/signIn/add")
    public Result addCourseSignIn(@RequestBody CourseSignIn courseSignIn) {
        log.info("---------------- 创建课程签到 ----------------------");
        String message = courseSignInService.add(courseSignIn);
        if ("创建成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @GetMapping("/api/class/signIn/all")
    public Result getAllCourseSignInByCourse(@RequestParam int cid) {
        log.info("---------------- 获取课程的所有签到 ----------------------");
        List<CourseSignIn> courseSignIns = courseSignInService.listAllByCourse(cid);
        return ResultFactory.buildSuccessResult(courseSignIns);
    }

    @GetMapping("/api/class/stu/signIn/now")
    public Result getCurrentCourseSignInByCourse(@RequestParam int cid) {
        log.info("---------------- 获取课程的当前签到 ----------------------");
        CourseSignIn courseSignIn = courseSignInService.getCurrentSignInByCourseId(cid);
        return ResultFactory.buildSuccessResult(courseSignIn);
    }

    @GetMapping("/api/class/signIn/end")
    public Result endCourseSignIn(@RequestParam int csuid) {
        log.info("---------------- 结束签到 ----------------------");
        String message= courseSignInService.endSignIn(csuid);
        if ("结束签到".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }


    /** -------------------------- 学生签到表 -------------------------------------- **/


    @PostMapping("/api/class/stu/signIn")
    public Result signIn(@RequestBody StudentSignIn studentSignIn) {
        log.info("---------------- 学生签到 ----------------------");
        String message= studentSignInService.add(studentSignIn);
        if ("签到成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else if ("请勿重复签到！".equals(message)) {
            return ResultFactory.buildResult(Constant.SIGNUP_REPEAT, message, null);
        }else if ("超出签到范围".equals(message)) {
            return ResultFactory.buildResult(Constant.SIGNUP_OUT_RANGE, message, null);
        }else if ("参数异常，签到失败".equals(message)) {
            return ResultFactory.buildFailResult(message);
        }else {
            return ResultFactory.buildResult(Constant.SIGNUP_VALUE_ERROR, message, null);
        }
    }

    @GetMapping("/api/class/stu/signIn/all")
    public Result getAllSignInByCourse(@RequestParam int cid) {
        log.info("---------------- 学生所有签到 ----------------------");
        List<Map<String, Object>> maps = studentSignInService.getAllSignInByUserId(userService.getCurrentUserId(), cid);
        return ResultFactory.buildSuccessResult(maps);
    }

    @GetMapping("/api/class/signIn/stu")
    public Result getAllSignInStudentByCourseSignIn(@RequestParam int csiid) {
        log.info("---------------- 课程签到的学生 ----------------------");
        List<Map<String, Object>> maps = studentSignInService.getAllSignInByCourseSignIn(csiid);
        return ResultFactory.buildSuccessResult(maps);
    }

}
