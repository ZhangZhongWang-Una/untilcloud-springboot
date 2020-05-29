package com.una.uc.util;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

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

    public static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid){

        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);

        return geoCurve.getEllipsoidalDistance();
    }
}
