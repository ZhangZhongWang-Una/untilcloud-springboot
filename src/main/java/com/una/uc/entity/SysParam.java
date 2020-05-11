package com.una.uc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Una
 * @date 2020/5/10 14:54
 */
@Entity
@Table(name = "sys_param")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class SysParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * signin_experience
     */
    private String key1;

    /**
     * 系统参数值
     */
    private String value1;

    /**
     * signin_range
     */
    private String key2;

    /**
     * 系统参数值
     */
    private String value2;

    /**
     * class_time
     */
    private String key3;

    /**
     * 系统参数值
     */
    private String value3;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 用户ID
     */
    @ManyToOne()
    @JoinColumn(name = "user_id")
    User user;

    public int getId() {
        return id;
    }

    public SysParam() {
    }

    public SysParam(Date updateTime, User user) {
        this.key1 = "signin_experience";
        this.value1 = "2";
        this.key2 = "signin_range";
        this.value2 = "20";
        this.key3 = "class_time";
        this.value3 = "45";
        this.updateTime = updateTime;
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserId() {
        return user.getId();
    }

    public String getUserUsername() {
        return user.getUsername();
    }

    public String getUserName() {
        return user.getName();
    }

    public boolean getUserEnabled() {
        return user.isEnabled();
    }
}
