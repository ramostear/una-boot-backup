package com.ramostear.unaboot.common.util;

import java.util.Random;

/**
 * Created by Ramostear on 2019/11/13 0013.
 */
public class RandomUtils {

    private RandomUtils(){}

    public static String next(int length){
        if(length <=0) length = 8;
        String val = "";
        Random random = new Random();
        for(int i=0;i<length;i++){
            String str = random.nextInt(2)%2 == 0 ? "num":"char";
            if("char".equalsIgnoreCase(str)){
                int nextInt = random.nextInt(2)%2 == 0?65:97;
                val += (char)(nextInt + random.nextInt(26));
            }else if("num".equalsIgnoreCase(str)){
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val.toLowerCase();
    }
}
