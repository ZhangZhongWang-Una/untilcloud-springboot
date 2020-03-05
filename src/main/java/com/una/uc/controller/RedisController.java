package com.una.uc.controller;

import com.una.uc.common.Result;
import com.una.uc.common.ResultFactory;
import com.una.uc.util.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author Una
 * @date 2020/3/5 23:40
 */
@Controller
public class RedisController {
    private static int ExpireTime = 120;   // redis中存储的过期时间60s

    @Resource
    private RedisUtil redisUtil;

    @GetMapping("/redis/set")
    @ResponseBody
    public Result redisset(@RequestParam("key")String key, @RequestParam("value")String value){
        boolean isSuccess = redisUtil.set(key, value, 20);
        if (true == isSuccess) {
            String message = "成功";
            return ResultFactory.buildSuccessResult(message);
        } else {
            String message = "失败";
            return ResultFactory.buildFailResult(message);
        }
    }

    @GetMapping("/redis/get")
    @ResponseBody
    public Result redisget(@RequestParam("key")String key){
        return ResultFactory.buildSuccessResult(redisUtil.get(key));
    }

    @GetMapping("/redis/expire")
    @ResponseBody
    public Result expire(@RequestParam("key")String key){
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
