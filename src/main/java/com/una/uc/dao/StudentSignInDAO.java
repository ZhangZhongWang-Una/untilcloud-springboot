package com.una.uc.dao;

import com.una.uc.entity.StudentSignIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Una
 * @date 2020/5/19 22:58
 */
public interface StudentSignInDAO extends JpaRepository<StudentSignIn,Integer> {
    @Query(value = "from StudentSignIn where courseSignIn.id = ?1 and student =?2")
    StudentSignIn findByCourseSignInAndStudent(int cid, int uid);

    @Query(value = "from StudentSignIn  where student = ?1 and courseSignIn.course.id = ?2 ")
    List<StudentSignIn> findAllByStudentAndCourseId(int uid, int cid);
}
