package com.una.uc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Una
 * @date 2020/5/19 14:55
 */
@Entity
@Table(name = "student_signin")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class StudentSignIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * 签到方式
     */
    private String mode;

    /**
     * 签到值
     */
    private String value;

    /**
     * 签到时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 学生
     */
    private int student;

    /**
     * 课程
     */
    @ManyToOne()
    @JoinColumn(name = "course_signin_id")
    private CourseSignIn courseSignIn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getStudent() {
        return student;
    }

    public void setStudent(int student) {
        this.student = student;
    }

    public CourseSignIn getCourseSignIn() {
        return courseSignIn;
    }

    public void setCourseSignIn(CourseSignIn courseSignIn) {
        this.courseSignIn = courseSignIn;
    }
}
