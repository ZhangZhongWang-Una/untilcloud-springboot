package com.una.uc.dao;

import com.una.uc.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Una
 * @date 2020/5/19 22:55
 */
public interface CourseDAO extends JpaRepository<Course,Integer> {
    Course findById(int id);

    List<Course> findAllByCreator(int id);
}
