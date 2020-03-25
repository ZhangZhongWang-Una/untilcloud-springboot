package com.una.uc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Una
 * @date 2020/3/19 17:30
 */

@Entity
@Table(name = "dictionary_type")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class DictionaryType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * 编码
     */
    private int code;

    /**
     * 字典类型名称
     */
    private String name;

    /**
     * 状态
     */
    private boolean status;

    /**
     * 字典信息
     */
    @OneToMany(mappedBy = "dictionaryType",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    List<DictionaryInfo> dictionaryInfos = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<DictionaryInfo> getDictionaryInfos() {
        return dictionaryInfos;
    }

    public void setDictionaryInfos(List<DictionaryInfo> dictionaryInfos) {
        this.dictionaryInfos = dictionaryInfos;
    }
}
