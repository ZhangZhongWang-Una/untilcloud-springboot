package com.una.uc.common;

public enum Constant {
    //请求返回
    SUCCESS(200),
    FAIL(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    //签到
    SIGNUP_REPEAT(701),
    SIGNUP_OUT_RANGE(702),
    SIGNUP_VALUE_ERROR(703),
    //短信验证码
    SMS_Verification_Code(801),
    //系统参数
    Sys_Param_Experience(2),
    Sys_Param_distance(100),

    //文件
    FILE_Url_Course("http://47.98.142.113:8443/api/file/Course/"),
    FILE_Url_User("http://47.98.142.113:8443/api/file/User/"),
    FILE_Url_QrCode("http://47.98.142.113:8443/api/file/QrCode/"),
    FILE_Url_Base("http://47.98.142.113:8443/api/file/"),
    FILE_Photo_User("C:/img/User/"),
    FILE_Photo_Course("C:/img/Course/"),
    FILE_QrCode("C:/img/QrCode/"),

    //签到
    SIGNUP_Mode_Gesture("gesture"),
    SIGNUP_Mode_Time("time");


    public int code;
    public String string;

    Constant(int code) {
        this.code = code;
    }

    Constant(String string) {
        this.string = string;
    }
}
