package com.una.uc.dao;

import com.una.uc.entity.StudentSignIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author Una
 * @date 2020/5/19 22:58
 */
public interface StudentSignInDAO extends JpaRepository<StudentSignIn,Integer> {
    @Query(value = "from StudentSignIn where courseSignIn.id = ?1 and student =?2")
    StudentSignIn findByCourseSignInAndStudent(int cid, int uid);

    @Query(value = "from StudentSignIn  where student = ?1 and courseSignIn.course.id = ?2 ")
    List<StudentSignIn> findAllByStudentAndCourseId(int uid, int cid);

    @Query(nativeQuery = true, value = "select ss.time, ss.mode ,ui.ino, ui.name from student_signin ss " +
            " left join `user_info` ui on ui.user_id= ss.student " +
            " where ss.course_signin_id = ?1 order by ino asc")
    List<Map<String,Object>> findAllByCourseSignIn(int csiid);

}
