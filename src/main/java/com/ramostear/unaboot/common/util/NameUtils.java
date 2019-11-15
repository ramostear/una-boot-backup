package com.ramostear.unaboot.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
public class NameUtils {

    private NameUtils(){}

    public static String humpToUnderline(String humpName){
        humpName = StringUtils.uncapitalize(humpName);
        char[] letters = humpName.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char letter:letters){
            if(Character.isUpperCase(letter)){
                sb.append("_"+letter+"");
            }else{
                sb.append(letter+"");
            }
        }
        return StringUtils.lowerCase(sb.toString());
    }
}
