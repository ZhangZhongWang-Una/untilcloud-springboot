package com.una.uc.service;

import com.una.uc.dao.DictionaryInfoDAO;
import com.una.uc.entity.DictionaryInfo;
import com.una.uc.entity.DictionaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Una
 * @date 2020/3/19 18:15
 */
@Service
public class DictionaryInfoService {
    @Autowired
    DictionaryInfoDAO dictionaryInfoDAO;
    @Autowired
    DictionaryTypeService dictionaryTypeService;

    public void addOrUpdate(DictionaryInfo dictionaryInfo) {
        dictionaryInfoDAO.save(dictionaryInfo);
    }

    public DictionaryInfo findById(int id) {
        return dictionaryInfoDAO.findById(id);
    }

    public List<DictionaryInfo> list() {
        return dictionaryInfoDAO.findAll();
    }

    public boolean isExist(DictionaryInfo info) {
        DictionaryInfo infoInDB = dictionaryInfoDAO.findByValueAndDictionaryType(info.getValue(), info.getDictionaryType().getId());
        return null!=infoInDB;
    }

    public  String add(DictionaryInfo dictionaryInfo) {
        String message = "";
        try {
            if (null == dictionaryInfo.getDictionaryType()) {
                message = "字典类型为空，添加失败";
                return message;
            }
            if (isExist(dictionaryInfo)) {
                message = "字典明细已存在，添加失败";
                return message;
            }
            dictionaryInfo.setUpdateTime(new Date());

            addOrUpdate(dictionaryInfo);

            message = "添加成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数错误，添加失败";
        }

        return message;
    }

    public String batchAdd(List<DictionaryInfo> dictionaryInfos) {
        String message = "";
        for (DictionaryInfo info : dictionaryInfos) {
            message = add(info);
            if (!"添加成功".equals(message))
                break;
        }
        return message;
    }

    public String delete(int dicInfoId) {
        String message = "";
        try {
            dictionaryInfoDAO.deleteById(dicInfoId);

            message = "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数错误，删除失败";
        }

        return message;
    }

    public  String edit(DictionaryInfo dictionaryInfo) {
        String message = "";
        try {
            DictionaryInfo dicInfoInDB = findById(dictionaryInfo.getId());

            if (null == dicInfoInDB) {
                message = "找不到该字典明细，修改失败";
                return message;
            }

            dicInfoInDB.setName(dictionaryInfo.getName());
            dicInfoInDB.setSort(dictionaryInfo.getSort());
            dicInfoInDB.setValue(dictionaryInfo.getValue());
            // dicInfoInDB.setDictionaryType();

            addOrUpdate(dicInfoInDB);
            message = "修改成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数错误，修改失败";
        }

        return message;
    }

    public  String updateStatus(DictionaryInfo dictionaryInfo) {
        String message = "";
        try {
            DictionaryInfo dicInfoInDB = findById(dictionaryInfo.getId());

            if (null == dicInfoInDB) {
                message = "找不到该字典明细，更新失败";
                return message;
            }

            dicInfoInDB.setStatus(dictionaryInfo.isStatus());

            addOrUpdate(dicInfoInDB);
            message = "更新成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "参数错误，更新失败";
        }

        return message;
    }

    public List<DictionaryInfo> findAllByTypeId(int dicTypeId) {
        return dictionaryInfoDAO.findAllByDictionaryTypeOrderBySort(dicTypeId);
    }

    public List<Map<String,String>> findAllByTypeCode(int code) {
        return dictionaryInfoDAO.findAllByCode(code);
    }

    public String addTypeAndInfos(DictionaryType dictionaryType, List<DictionaryInfo> dictionaryInfos) {
        String message = "";
        try {
            dictionaryTypeService.addOrUpdate(dictionaryType);
            int id = dictionaryTypeService.getIdByCode(dictionaryType.getCode());
            dictionaryType.setId(id);
            for (DictionaryInfo dictionaryInfo: dictionaryInfos) {
                dictionaryInfo.setDictionaryType(dictionaryType);
                addOrUpdate(dictionaryInfo);
            }

            message = "添加成功";
        } catch (Exception e) {
            message = "参数异常，添加失败";
        }

        return message;
    }
}
