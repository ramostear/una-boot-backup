package com.ramostear.unaboot.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @ClassName PinyinUtils
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/13 0013 2:21
 * @Version 1.0
 **/
public class PinyinUtils {

    private PinyinUtils(){}

    /**
     * 将中文转为为汉字拼音首字母字符串
     * @param chinese       中文汉字
     * @return              首字母字符串
     */
    public static String convertToFirstChar(String chinese){
        String pinyin = "";
        char[] chars = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for(char c : chars){
            if(c > 128){
                try{
                    pinyin += PinyinHelper.toHanyuPinyinStringArray(c,defaultFormat)[0].charAt(0);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            }else {
                pinyin += c;
            }
        }
        return pinyin.toLowerCase();
    }


    public static String convertTo(String chinese){
        String pinyin = "";
        char[] chars = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for(char c : chars){
            if(c > 128){
                try {
                    pinyin += PinyinHelper.toHanyuPinyinStringArray(c,defaultFormat)[0];
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            }else{
                pinyin += c;
            }
        }
        return pinyin.toLowerCase();
    }
}
