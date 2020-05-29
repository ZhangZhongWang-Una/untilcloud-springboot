package com.una.uc.controller;

import com.una.uc.common.Result;
import com.una.uc.common.ResultFactory;
import com.una.uc.entity.SchoolInstitution;
import com.una.uc.entity.SysParam;
import com.una.uc.service.SchoolInstitutionService;
import com.una.uc.service.SysParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Una
 * @date 2020/5/10 15:02
 */
@Slf4j
@RestController
public class SysController {
    @Autowired
    SysParamService sysParamService;
    @Autowired
    SchoolInstitutionService schoolInstitutionService;

    /** -------------------------- 系统参数 -------------------------------------- **/

    @GetMapping(value = "/api/sys/param/all")
    public Result getAllSysParam() {
        log.info("---------------- 获取所有系统参数 ----------------------");
        List<SysParam> sysParams= sysParamService.list();
        return ResultFactory.buildSuccessResult(sysParams);
    }

    @PutMapping(value = "/api/sys/param/edit")
    public Result editSysParam(@RequestBody SysParam sysParam) {
        log.info("---------------- 修改系统参数 ----------------------");
        String message = sysParamService.edit(sysParam);
        if ("修改成功".equals(message)) {
            return ResultFactory.buildSuccessResult(message);
        }
        else {
            return ResultFactory.buildFailResult(message);
        }
    }

    @GetMapping(value = "/api/sys/param/search")
    public Result searchSysParam(@RequestParam String keywords) {
        log.info("---------------- 搜索系统参数 ----------------------");
        List<SysParam> sysParams= sysParamService.search(keywords);
        return ResultFactory.buildSuccessResult(sysParams);
    }

    /** -------------------------- 学校机构 -------------------------------------- **/

    @GetMapping(value = "/api/sys/school/all")
    public Result getAllSchoolInstitution() {
        log.info("---------------- 获取所有学校机构 ----------------------");
        List<SchoolInstitution> schoolInstitutions= schoolInstitutionService.list();
        return ResultFactory.buildSuccessResult(schoolInstitutions);
    }

    @PostMapping(value = "/api/sys/school/add")
    public Result addSchoolInstitution(@RequestBody SchoolInstitution sInstitution) {
        log.info("---------------- 增加学校机构 ----------------------");
        String message = schoolInstitutionService.add(sInstitution);
        if ("添加成功".equals(message)) {
            return ResultFactory.buildSuccessResult(message);
        }
        else {
            return ResultFactory.buildFailResult(message);
        }
    }

    @GetMapping(value = "/api/sys/school/delete")
    public Result deleteSchoolInstitution(@RequestParam int siid) {
        log.info("---------------- 删除学校机构 ----------------------");
        String message = schoolInstitutionService.delete(siid);
        if ("删除成功".equals(message)) {
            return ResultFactory.buildSuccessResult(message);
        }
        else {
            return ResultFactory.buildFailResult(message);
        }
    }

    @PutMapping(value = "/api/sys/school/edit")
    public Result editSchoolInstitution(@RequestBody SchoolInstitution sInstitution) {
        log.info("---------------- 修改学校机构 ----------------------");
        String message = schoolInstitutionService.edit(sInstitution);
        if ("修改成功".equals(message)) {
            return ResultFactory.buildSuccessResult(message);
        }
        else {
            return ResultFactory.buildFailResult(message);
        }
    }

    @GetMapping(value = "/api/sys/school/search")
    public Result searchSchoolInstitution(@RequestParam String keywords) {
        log.info("---------------- 搜索学校机构 ----------------------");
        List<SchoolInstitution> schoolInstitutions= schoolInstitutionService.search(keywords);
        return ResultFactory.buildSuccessResult(schoolInstitutions);
    }

    @GetMapping("/api/sys/school/get")
    public Result getSchool() {
        log.info("---------------- 获取学校级别 ----------------------");
        List<SchoolInstitution> schoolInstitutions= schoolInstitutionService.getSchool(0);
        return ResultFactory.buildSuccessResult(schoolInstitutions);
    }

    @GetMapping("/api/sys/school/get/{parentId}")
    public Result getCollegeAndMajor(@PathVariable("parentId") int parentId) {
        log.info("---------------- 获取院校系 ----------------------");
        List<SchoolInstitution> schoolInstitutions= schoolInstitutionService.getSchool(parentId);
        return ResultFactory.buildSuccessResult(schoolInstitutions);
    }
}
