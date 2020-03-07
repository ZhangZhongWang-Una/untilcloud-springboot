package com.una.uc.controller;

import com.una.uc.common.Result;
import com.una.uc.common.ResultFactory;
import com.una.uc.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Una
 * @date 2020/3/5 23:40
 */
@Slf4j
@RestController
public class RedisController {
    private static int ExpireTime = 120;   // redis中存储的过期时间60s

    @Resource
    private RedisUtil redisUtil;

    @GetMapping("/api/common/redis/set")
    public Result redisset(@RequestParam("key")String key, @RequestParam("value")String value){
        log.info("---------------- 获取缓存 ----------------------");
        boolean isSuccess = redisUtil.set(key, value, 20);
        if (true == isSuccess) {
            String message = "成功";
            return ResultFactory.buildSuccessResult(message);
        } else {
            String message = "失败";
            return ResultFactory.buildFailResult(message);
        }
    }

    @GetMapping("/api/common/redis/get")
    public Result redisget(@RequestParam("key")String key){
        log.info("----------------存入缓存 ----------------------");
        return ResultFactory.buildSuccessResult(redisUtil.get(key));
    }

    @GetMapping("/api/common/redis/expire")
    public Result expire(@RequestParam("key")String key){
        log.info("---------------- 设置缓存时间 ----------------------");
        boolean isSuccess = redisUtil.expire(key,ExpireTime);
        if (true == isSuccess) {
            String message = "成功";
            return ResultFactory.buildSuccessResult(message);
        } else {
            String message = "失败";
            return ResultFactory.buildFailResult(message);
        }
    }
}
