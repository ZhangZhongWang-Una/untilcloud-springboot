package com.una.uc.controller;

import com.una.uc.common.Result;
import com.una.uc.common.ResultFactory;
import com.una.uc.entity.AdminPermission;
import com.una.uc.service.AdminPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Una
 * @date 2020/3/17 20:02
 */
@Slf4j
@RestController
public class PermController {
    @Autowired
    AdminPermissionService adminPermissionService;

    @PostMapping("api/admin/perm/add")
    public Result addPerm(@RequestBody AdminPermission adminPermission){
        log.info("---------------- 添加权限 ----------------------");
        String message = adminPermissionService.add(adminPermission);
        if ("添加成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    @GetMapping("/api/admin/perm/delete")
    public Result deletePerm(@RequestParam int pid) {
        log.info("---------------- 删除权限 ----------------------");
        String message = adminPermissionService.delete(pid);
        if ("删除成功".equals(message)) {
            return ResultFactory.buildSuccessResult(message);
        } else {
            return ResultFactory.buildFailResult(message);
        }
    }

    @PostMapping("/api/admin/perm/delete")
    public Result batchDeletePerm(@RequestBody LinkedHashMap permIds) {
        log.info("---------------- 批量删除权限 ----------------------");
        String message = adminPermissionService.batchDelete(permIds);
        if ("删除成功".equals(message)) {
            return ResultFactory.buildSuccessResult(message);
        } else {
            return ResultFactory.buildFailResult(message);
        }
    }

    @PutMapping("/api/admin/perm/edit")
    public Result editPerm(@RequestBody AdminPermission adminPermission) {
        log.info("---------------- 修改权限 ----------------------");
        String message = adminPermissionService.edit(adminPermission);
        if ("修改成功".equals(message))
            return ResultFactory.buildSuccessResult(message);
        else
            return ResultFactory.buildFailResult(message);
    }

    // TODO 有问题需要优化
    @GetMapping(value = "/api/admin/perm/search")
    public Result search(@RequestParam String keywords){
        log.info("---------------- 搜索权限 ----------------------");
        List<AdminPermission> ps = adminPermissionService.search(keywords);
        return ResultFactory.buildSuccessResult(ps);
    }
}
