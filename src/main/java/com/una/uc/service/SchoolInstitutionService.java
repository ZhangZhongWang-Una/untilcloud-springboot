package com.una.uc.service;

import com.una.uc.dao.SchoolInstitutionDAO;
import com.una.uc.entity.SchoolInstitution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author Una
 * @date 2020/5/11 19:34
 */
@Service
public class SchoolInstitutionService {
    @Autowired
    SchoolInstitutionDAO schoolInstitutionDAO;

    public void addOrUpdate(SchoolInstitution schoolInstitution) {
        schoolInstitution.setUpdateTime(new Date());
        schoolInstitutionDAO.save(schoolInstitution);
    }

    public List<SchoolInstitution> getAllByParentId(int pid) {
        return schoolInstitutionDAO.findAllByParentId(pid);
    }

    public String setLevel(int pid){
        String level =schoolInstitutionDAO.findLevelByParentId(pid);
        if (StringUtils.isEmpty(level)) {
            level = String.valueOf(pid) + ".";
        } else {
            level = level  + String.valueOf(pid) + ".";
        }
        return level;
    }

    public String add(SchoolInstitution sInstitution) {
        String message = "";
        try {
            String level = setLevel(sInstitution.getParentId());
            sInstitution.setLevel(level);
            addOrUpdate(sInstitution);
            message = "添加成功";
        } catch (Exception e) {
            message = "参数异常，添加失败";
            e.printStackTrace();
        };

        return message;
    }

    @Modifying
    @Transactional
    public String delete(int siid) {
        String message = "";
        try {
            SchoolInstitution sInstitutionInDB = schoolInstitutionDAO.findById(siid);
            if (null == sInstitutionInDB) {
                message = "该机构未找到，删除失败";
            } else {
                String level = sInstitutionInDB.getLevel() + String.valueOf(siid) + ".%";
                schoolInstitutionDAO.delete(sInstitutionInDB);
                schoolInstitutionDAO.deleteAllByLevelLike(level);
                message = "删除成功";
            }

        } catch (Exception e) {
            message = "参数异常，删除失败";
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        };

        return message;
    }

    public String edit(SchoolInstitution sInstitution) {
        String message = "";
        try {
            SchoolInstitution sInstitutionInDB= schoolInstitutionDAO.findById(sInstitution.getId());
            if (null == sInstitutionInDB) {
                message = "该机构未找到，修改失败";
            } else {
                sInstitutionInDB.setName(sInstitution.getName());
                sInstitutionInDB.setParentId(sInstitution.getParentId());
                sInstitutionInDB.setSort(sInstitution.getSort());
                String level =setLevel(sInstitution.getParentId());
                sInstitutionInDB.setLevel(level);
                addOrUpdate(sInstitutionInDB);
                message = "修改成功";
            }

        } catch (Exception e) {
            message = "参数异常，修改失败";
            e.printStackTrace();
        };

        return message;
    }

    public List<SchoolInstitution> search(String keywords) {
        List<SchoolInstitution> sInstitutions = schoolInstitutionDAO.findAllByNameLike("%" + keywords + "%");
        List<SchoolInstitution> list = list();
        Iterator<SchoolInstitution> iterator = list.iterator();
        List<String> idList =new ArrayList<>();
        for (SchoolInstitution sInstitution: sInstitutions) {
            String[] tag = sInstitution.getLevel().split("\\.");
            idList.addAll(Arrays.asList(tag));
            idList.add(String.valueOf(sInstitution.getId()));
        }
        LinkedHashSet<String> hashSet =new LinkedHashSet<>(idList);
        while (iterator.hasNext()) {
            SchoolInstitution sInstitution = iterator.next();
            if (!hashSet.contains(String.valueOf(sInstitution.getId()))) {
                iterator.remove();
            }
        }
        return list;
    }

    public List<SchoolInstitution> list() {
        Sort sort = Sort.by(Sort.Direction.ASC, "sort");
        List<SchoolInstitution> sInstitutions = schoolInstitutionDAO.findAll(sort);

        for (SchoolInstitution sInstitution: sInstitutions) {
            sInstitution.setChildren(getAllByParentId(sInstitution.getId()));
        }

        Iterator<SchoolInstitution> iterator = sInstitutions.iterator();
        while (iterator.hasNext()) {
            SchoolInstitution sInstitution = iterator.next();
            if (sInstitution.getParentId() != 0) {
                iterator.remove();
            }
        }

        return sInstitutions;
    }

    public List<SchoolInstitution> handleSchoolInstitutions(List<SchoolInstitution> sInstitutions) {
        List<SchoolInstitution> deleteInstitutions = new ArrayList<>();
        for (SchoolInstitution sInstitution: sInstitutions){
            for (SchoolInstitution sInstitution2: sInstitutions) {
                if (sInstitution.getId() == sInstitution2.getParentId()) {
                    if (null == sInstitution.getChildren()) {
                        List<SchoolInstitution> children = new ArrayList<>();
                        children.add(sInstitution2);
                        sInstitution.setChildren(children);
                        deleteInstitutions.add(sInstitution2);
                    } else {
                        sInstitution.getChildren().add(sInstitution2);
                        deleteInstitutions.add(sInstitution2);
                    }
                }
            }
        }
        for (SchoolInstitution sInstitution: deleteInstitutions) {
            sInstitutions.remove(sInstitution);
        }
        return sInstitutions;
    }

    public List<SchoolInstitution> getSchool(int pid) {
        return schoolInstitutionDAO.findAllByParentId(pid);
    }
}
