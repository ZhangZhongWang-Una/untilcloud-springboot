package com.una.uc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Una
 * @date 2020/5/11 19:26
 */
@Entity
@Table(name = "school_institution")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class SchoolInstitution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     *学校名称或下属机构名称
     */
    private String name;

    /**
     *机构级别字符串
     */
    private String level;

    /**
     *排序
     */
    private int sort;

    /**
     *父级主键id
     */
    @Column(name = "parent_id")
    private int parentId;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 子机构
     */
    @Transient
    private List<SchoolInstitution> children;

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<SchoolInstitution> getChildren() {
        return children;
    }

    public void setChildren(List<SchoolInstitution> children) {
        this.children = children;
    }
}
