package com.una.uc.controller;

import com.una.uc.common.Result;
import com.una.uc.common.ResultFactory;
import com.una.uc.entity.SysParam;
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

//    @PutMapping(value = "/api/sys/param/status")
//    public Result updateSysParamStatus(@RequestBody SysParam sysParam) {
//        log.info("---------------- 更新系统参数状态 ----------------------");
//        String message = sysParamService.updateStatus(sysParam);
//        if ("更新成功".equals(message)) {
//            return ResultFactory.buildSuccessResult(message);
//        }
//        else {
//            return ResultFactory.buildFailResult(message);
//        }
//    }
//
//    @PostMapping(value = "/api/sys/param/add")
//    public Result addSysParam(@RequestBody SysParam sysParam) {
//        log.info("---------------- 添加新系统参数 ----------------------");
//        String message = sysParamService.add(sysParam);
//        if ("添加成功".equals(message)) {
//            return ResultFactory.buildSuccessResult(message);
//        }
//        else {
//            return ResultFactory.buildFailResult(message);
//        }
//    }
//
//    @GetMapping(value = "/api/sys/param/delete")
//    public Result deleteSysParam(@RequestParam int spid) {
//        log.info("---------------- 删除系统参数 ----------------------");
//        String message = sysParamService.delete(spid);
//        if ("删除成功".equals(message)) {
//            return ResultFactory.buildSuccessResult(message);
//        }
//        else {
//            return ResultFactory.buildFailResult(message);
//        }
//    }
//
//    @PostMapping(value = "/api/sys/param/delete")
//    public Result batchDeleteSysParam(@RequestBody LinkedHashMap sysParamIds) {
//        log.info("---------------- 批量删除系统参数 ----------------------");
//        String message = sysParamService.batchDelete(sysParamIds);
//        if ("删除成功".equals(message)) {
//            return ResultFactory.buildSuccessResult(message);
//        }
//        else {
//            return ResultFactory.buildFailResult(message);
//        }
//    }
}
