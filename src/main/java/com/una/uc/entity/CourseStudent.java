package com.una.uc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Una
 * @date 2020/5/19 14:37
 */
@Entity
@Table(name = "course_stu")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class CourseStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * 经验值
     */
    private int experience;

    /**
     * 学生
     */
    @ManyToOne()
    @JoinColumn(name = "stu_id")
    private User stu;

    /**
     * 课程
     */
    @ManyToOne()
    @JoinColumn(name = "course_id")
    private Course course;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public User getStu() {
        return stu;
    }

    public void setStu(User stu) {
        this.stu = stu;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
