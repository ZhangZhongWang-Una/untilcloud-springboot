package com.una.uc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

/**
 * @author Una
 * @date 2020/4/24 23:41
 */
@Entity
@Table(name = "user_info")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 学号/工号
     */
    private String ino;

    /**
     * 性别
     */
    private String sex;

    /**
     * 学校
     */
    private String school;

    /**
     * 学院
     */
    private String college;

    /**
     * 专业
     */
    private String major;

    /**
     * 头像
     */
    private String cover;

    /**
     * 用户ID
     */
    @ManyToOne()
    @JoinColumn(name = "user_id")
    User user;

    /**
     * 角色
     */
    @Transient
    private List<AdminRole> roles;

    /**
     * 真实姓名
     */
    @Transient
    private String name;

    public UserInfo() {}

    public UserInfo(int id, String username, String nickname, String ino, String sex,
                    String school, String college, String cover) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.ino = ino;
        this.sex = sex;
        this.school = school;
        this.college = college;
        this.cover = cover;
    }

    public UserInfo(String username,User user) {
        this.username = username;
        this.cover = "default.jpg";
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIno() {
        return ino;
    }

    public void setIno(String ino) {
        this.ino = ino;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AdminRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AdminRole> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
