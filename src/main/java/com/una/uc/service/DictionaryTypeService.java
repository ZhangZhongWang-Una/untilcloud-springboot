package com.una.uc.service;

import com.una.uc.dao.DictionaryTypeDAO;
import com.una.uc.entity.DictionaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Una
 * @date 2020/3/19 18:14
 */
@Service
public class DictionaryTypeService {
    @Autowired
    DictionaryTypeDAO dictionaryTypeDAO;

    DictionaryType findById(int id){
        return dictionaryTypeDAO.findById(id);
    }

    public void addOrUpdate(DictionaryType dictionaryType) {
        dictionaryTypeDAO.save(dictionaryType);
    }

    public List<DictionaryType> list() {
        return dictionaryTypeDAO.findAll();
    }

    public  String add(DictionaryType dictionaryType) {
        String message = "";
        try {
            addOrUpdate(dictionaryType);

            message = "添加成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数错误，添加失败";
        }

        return message;
    }

    public  String delete(int dicTypeId) {
        String message = "";
        try {
            dictionaryTypeDAO.deleteById(dicTypeId);

            message = "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数错误，删除失败";
        }

        return message;
    }

    public String edit(DictionaryType dictionaryType){
        String message = "";
        try {
            DictionaryType dicTypeInDB = findById(dictionaryType.getId());
            if (null == dicTypeInDB) {
                message = "找不到该字典类型，修改失败";
                return message;
            }

            dicTypeInDB.setCode(dictionaryType.getCode());
            dicTypeInDB.setName(dictionaryType.getName());
            addOrUpdate(dicTypeInDB);

            message = "修改成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数错误，修改失败";
        }

        return message;
    }

    public String updateStatus(DictionaryType dictionaryType){
        String message = "";
        try {
            DictionaryType dicTypeInDB = findById(dictionaryType.getId());

            if (null == dicTypeInDB) {
                message = "找不到该字典类型，更新失败";
                return message;
            }
            dicTypeInDB.setStatus(dictionaryType.isStatus());
            addOrUpdate(dicTypeInDB);

            message = "更新成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数错误，更新失败";
        }

        return message;
    }
}
