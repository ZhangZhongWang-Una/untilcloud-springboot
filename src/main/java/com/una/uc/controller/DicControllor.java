package com.una.uc.controller;

import com.una.uc.common.Result;
import com.una.uc.common.ResultFactory;
import com.una.uc.entity.DictionaryInfo;
import com.una.uc.entity.DictionaryType;
import com.una.uc.service.DictionaryInfoService;
import com.una.uc.service.DictionaryTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Una
 * @date 2020/3/19 18:16
 */
@Slf4j
@RestController
public class DicControllor {
    @Autowired
    DictionaryInfoService dictionaryInfoService;
    @Autowired
    DictionaryTypeService dictionaryTypeService;

    /** -------------------------- 字典类型 -------------------------------------- **/
    @PostMapping("/api/sys/dic/type/add")
    public Result addDicType(@RequestBody DictionaryType dictionaryType) {
        log.info("---------------- 增加字典类型 ----------------------");
        String message = dictionaryTypeService.add(dictionaryType);
        if ("添加成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @GetMapping("/api/sys/dic/type/delete")
    public Result deleteDicType(@RequestParam int dicTypeId) {
        log.info("---------------- 删除字典类型 ----------------------");
        String message = dictionaryTypeService.delete(dicTypeId);
        if ("删除成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @PutMapping("/api/sys/dic/type/edit")
    public Result editDicType(@RequestBody DictionaryType dictionaryType) {
        log.info("---------------- 修改字典类型 ----------------------");
        String message = dictionaryTypeService.edit(dictionaryType);
        if ("修改成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @PutMapping("/api/sys/dic/type/status")
    public Result updateDicTypeStatus(@RequestBody DictionaryType dictionaryType) {
        log.info("---------------- 修改字典类型状态 ----------------------");
        String message = dictionaryTypeService.updateStatus(dictionaryType);
        if ("更新成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @GetMapping("/api/sys/dic/type/all")
    public Result getAllDicType() {
        log.info("---------------- 获取所有字典类型 ----------------------");
        List<DictionaryType> dts = dictionaryTypeService.list();

        return ResultFactory.buildSuccessResult(dts);
    }

    /** -------------------------- 字典明细 -------------------------------------- **/
    @PostMapping("/api/sys/dic/info/add")
    public Result addDicInfo(@RequestBody DictionaryInfo dictionaryInfo) {
        log.info("---------------- 增加字典类型 ----------------------");
        String message = dictionaryInfoService.add(dictionaryInfo);
        if ("添加成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @GetMapping("/api/sys/dic/info/delete")
    public Result deleteDicInfo(@RequestParam int dicInfoId) {
        log.info("---------------- 删除字典类型 ----------------------");
        String message = dictionaryInfoService.delete(dicInfoId);
        if ("删除成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @PutMapping("/api/sys/dic/info/edit")
    public Result editDicInfo(@RequestBody DictionaryInfo dictionaryInfo) {
        log.info("---------------- 修改字典类型 ----------------------");
        String message = dictionaryInfoService.edit(dictionaryInfo);
        if ("修改成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @PutMapping("/api/sys/dic/info/status")
    public Result updateDicInfoStatus(@RequestBody DictionaryInfo dictionaryInfo) {
        log.info("---------------- 修改字典类型状态 ----------------------");
        String message = dictionaryInfoService.updateStatus(dictionaryInfo);
        if ("更新成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

//    @GetMapping("/api/sys/dic/info/all")
//    public Result getAllDicInfo() {
//        log.info("---------------- 获取所有字典类型 ----------------------");
//        List<DictionaryInfo> dis = dictionaryInfoService.list();
//
//        return ResultFactory.buildSuccessResult(dis);
//    }

    @GetMapping("/api/sys/dic/info/all")
    public Result getAllDicInfoByDicTypeId(@RequestParam int dicTypeId) {
        log.info("---------------- 获取所有字典类型 ----------------------");
        List<DictionaryInfo> dis = dictionaryInfoService.findAllByTypeId(dicTypeId);

        return ResultFactory.buildSuccessResult(dis);
    }

    @GetMapping("/api/sys/dic/get/{TypeCode}")
    public Result getAllByTypeCode(@PathVariable("TypeCode") int typeCode) {
        log.info("---------------- 获取字典键值对 ----------------------");
        List<Map<String, String>> kv = dictionaryInfoService.findAllByTypeCode(typeCode);

        return ResultFactory.buildSuccessResult(kv);
    }
}
