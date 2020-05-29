package com.una.uc.dao;


import com.una.uc.entity.Course;
import com.una.uc.entity.CourseStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author Una
 * @date 2020/5/19 22:56
 */
public interface CourseStudentDAO extends JpaRepository<CourseStudent,Integer> {
    @Query(nativeQuery = true, value = "select cs.id, cs.experience, ui.ino, ui.name,ui.cover,cs.stu_id " +
            "from course_stu cs left join user_info ui on ui.user_id = cs.stu_id where cs.course_id = ?1 ")
    List<Map<String,String>> findAllStudentByCourseId(int cid);

    @Query(value = "select cs.course from  CourseStudent cs where cs.stu.id = ?1 ")
    List<Course> findCourseByUserId(int uid);

    @Query(value = "from  CourseStudent cs where cs.course.id = ?1 and cs.stu.id = ?2 ")
    CourseStudent findByCourseAndStu(int cid, int uid);
}
