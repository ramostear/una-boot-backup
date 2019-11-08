package com.ramostear.unaboot.common.util;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName DateUtils
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/8 0008 23:53
 * @Version 1.0
 **/
public class DateUtils {

    private DateUtils(){}

    @NonNull
    public static Date now(){
        return new Date();
    }

    @NonNull
    public static Calendar convertTo(@NonNull Date date){
        Assert.notNull(date,"Date must be not null.");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }


    public static Date add(@NonNull Date date, long time, @NonNull TimeUnit unit){
        Assert.notNull(date,"Date must be not null");
        Assert.isTrue(time >=0,"add time must not be less than 1");
        Assert.notNull(unit,"time unit must not be null");

        Date newDate;
        int timeIntValue;

        if(time > Integer.MAX_VALUE){
            timeIntValue = Integer.MAX_VALUE;
        }else{
            timeIntValue = Long.valueOf(time).intValue();
        }

        switch (unit){
            case DAYS:
                newDate = org.apache.commons.lang3.time.DateUtils.addDays(date,timeIntValue);
                break;
            case HOURS:
                newDate = org.apache.commons.lang3.time.DateUtils.addHours(date,timeIntValue);
                break;
            case MINUTES:
                newDate = org.apache.commons.lang3.time.DateUtils.addMinutes(date,timeIntValue);
                break;
            case SECONDS:
                newDate = org.apache.commons.lang3.time.DateUtils.addSeconds(date,timeIntValue);
                break;
            case MICROSECONDS:
                newDate = org.apache.commons.lang3.time.DateUtils.addMilliseconds(date,timeIntValue);
                break;
            default:
                newDate = date;
        }
        return newDate;
    }

    public static Date getServerStartDate(){
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    public static String parseTo(Date date,String pattern){
        if(ObjectUtils.allNotNull(date)){
            return new SimpleDateFormat(pattern).format(date);
        }
        return null;
    }

    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = (long)1000 * 24 * 60 * 60;
        long nh = (long)1000 * 60 * 60;
        long nm = (long)1000 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        return day + "天" + hour + "小时" + min + "分钟" ;
    }
}
