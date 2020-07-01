package com.una.uc.common;

public class ResultFactory {
    public static Result buildSuccessResult(Object data) {
        return buildResult(Constant.SUCCESS.code, "成功", data);
    }

    public static Result buildFailResult(String message) {
        return buildResult(Constant.FAIL.code, message, null);
    }

    public static Result buildResult(Constant constant, String message, Object data) {
        return buildResult(constant.code, message, data);
    }

    public static Result buildResult(int resultCode, String message, Object data) {
        return new Result(resultCode, message, data);
    }
}
