package com.una.uc.common;

/**
 * @author Una
 * @date 2020/3/12 22:48
 */
public enum RoleConstant {
    SUPER_ADMIN(1),
    SYS_ADMIN(2),
    TEACHER(3),
    ASSISTANT(4),
    STUDENT(5),
    BASE(8),
    TEST(9);

    public int code;
    RoleConstant(int code) {
        this.code = code;
    }
}
