package com.una.uc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.util.Date;

/**
 * @author Una
 * @date 2020/5/19 14:21
 */
@Entity
@Table(name = "course")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     *  课程名
     */
    private String name;

    /**
     *  年级
     */
    private String grade;

    /**
     * 学期
     */
    private String semester;

    /**
     * 学校
     */
    private String school;

    /**
     * 院系
     */
    private String college;

    /**
     * 专业
     */
    private String major;

    /**
     * 任课教师
     */
    private String teacher;

    /**
     * 学习要求
     */
    @Column(name = "learn_require")
    private String learnRequire;

    /**
     * 教学进度
     */
    @Column(name= "teach_progress")
    private String teachProgress;

    /**
     * 考试安排
     */
    @Column(name="exam_arrange")
    private String examArrange;

    /**
     * 封面
     */
    private String cover;

    /**
     * 二维码
     */
    private String qrcode;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建人
     */
    private int creator;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getLearnRequire() {
        return learnRequire;
    }

    public void setLearnRequire(String learnRequire) {
        this.learnRequire = learnRequire;
    }

    public String getTeachProgress() {
        return teachProgress;
    }

    public void setTeachProgress(String teachProgress) {
        this.teachProgress = teachProgress;
    }

    public String getExamArrange() {
        return examArrange;
    }

    public void setExamArrange(String examArrange) {
        this.examArrange = examArrange;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }
}
