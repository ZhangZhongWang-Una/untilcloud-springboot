package com.una.uc.common;

public enum Constant {
    SUCCESS(200),
    FAIL(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    // 短信验证码
    SMS_Verification_Code(801);

    public int code;

    Constant(int code) {
        this.code = code;
    }
}
