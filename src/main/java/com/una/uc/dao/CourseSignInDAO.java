package com.una.uc.dao;

import com.una.uc.entity.CourseSignIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author Una
 * @date 2020/5/19 22:58
 */
public interface CourseSignInDAO extends JpaRepository<CourseSignIn,Integer> {
    @Query(value = "select new CourseSignIn(cs.id, cs.mode, cs.startTime, cs.endTime) from CourseSignIn cs where cs.course.id = ?1 order by cs.startTime desc ")
    List<CourseSignIn> findAllByCourse(int cid);

    CourseSignIn findById(int id);

    @Query(value = "from CourseSignIn where course.id = ?1 and startTime < ?2 and (endTime > ?2 or endTime = '' or endTime is NULL )")
    CourseSignIn findByCourseIdAndDate(int cid, Date date);
}
