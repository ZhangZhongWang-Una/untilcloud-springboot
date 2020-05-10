package com.una.uc.util;

import java.util.Random;
import java.util.UUID;

/**
 * @author Una
 * @date 2020/3/5 21:13
 */
public class CommonUtil {
    public static String createCode(int count) {
        // String str="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String str = "0123456789";
        StringBuilder sb=new StringBuilder(count);
        for(int i=0;i<count;i++)
        {
            char ch=str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }

    public static String creatUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
