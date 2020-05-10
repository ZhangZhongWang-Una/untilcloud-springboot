package com.una.uc.service;

import com.una.uc.dao.SysParamDAO;
import com.una.uc.entity.SysParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * @author Una
 * @date 2020/5/10 14:59
 */
@Service
public class SysParamService {
    @Autowired
    SysParamDAO sysParamDAO;

    public void addOrUpdate(SysParam sysParam) {
        sysParamDAO.save(sysParam);
    }

    public String add(SysParam sysParam) {
        String message = "";
        try {
            sysParam.setUpdateTime(new Date());
            addOrUpdate(sysParam);
            message = "添加成功";
        } catch (Exception e) {
            message = "参数异常，添加失败";
            e.printStackTrace();
        }

        return message;
    }

    public String delete(int spid) {
        String message = "";
        try {
            SysParam sysParamInDB = sysParamDAO.findById(spid);
            if (null == sysParamInDB) {
                message = "该系统参数不存在，删除失败";
            }
            else {
                sysParamDAO.delete(sysParamInDB);
                message = "删除成功";
            }
        } catch (Exception e) {
            message = "参数异常，删除失败";
            e.printStackTrace();
        }

        return message;
    }

    public String batchDelete(LinkedHashMap sysParamIds) {
        String message = "";
        for (Object value : sysParamIds.values()) {
            for (int spid :(List<Integer>)value) {
                message = delete(spid);
                if (!"删除成功".equals(message)) {
                    break;
                }
            }
        }
        return message;
    }

    public String edit(SysParam sysParam) {
        String message = "";
        try {
            SysParam sysParamInDB = sysParamDAO.findById(sysParam.getId());
            if (null == sysParamInDB) {
                message = "该系统参数不存在，修改失败";
            }
            else {
                sysParamInDB.setName(sysParam.getName());
                sysParamInDB.setNameZh(sysParam.getNameZh());
                sysParamInDB.setValue(sysParam.getValue());
                addOrUpdate(sysParamInDB);
                message = "修改成功";
            }
        } catch (Exception e) {
            message = "参数异常，修改失败";
            e.printStackTrace();
        }

        return message;
    }

    public String updateStatus(SysParam sysParam) {
        String message = "";
        try {
            SysParam sysParamInDB = sysParamDAO.findById(sysParam.getId());
            if (null == sysParamInDB) {
                message = "该系统参数不存在，更新失败";
            }
            else {
                sysParamInDB.setStatus(sysParam.isStatus());
                addOrUpdate(sysParamInDB);
                message = "更新成功";
            }
        } catch (Exception e) {
            message = "参数异常，更新失败";
            e.printStackTrace();
        }

        return message;
    }

    public List<SysParam> search(String keywords) {
        return sysParamDAO.findAllByNameLikeOrNameZhLikeOrValueLike('%' + keywords + '%',
                '%' + keywords + '%', '%' + keywords + '%');
    }

    public List<SysParam> list() {
        return sysParamDAO.findAll();
    }
}
