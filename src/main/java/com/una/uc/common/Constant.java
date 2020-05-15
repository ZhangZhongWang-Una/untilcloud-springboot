package com.una.uc.common;

public enum Constant {
    //请求返回
    SUCCESS(200),
    FAIL(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    // 短信验证码
    SMS_Verification_Code(801),

    //文件
    //FILE_Photo_Base_Url("http://localhost:8443/api/file/"),
    FILE_Photo_Base_Url("http://47.98.142.113:8443/api/file/"),
    FILE_Photo_Path("C:/img");

    public int code;
    public String string;

    Constant(int code) {
        this.code = code;
    }

    Constant(String string) {
        this.string = string;
    }
}
