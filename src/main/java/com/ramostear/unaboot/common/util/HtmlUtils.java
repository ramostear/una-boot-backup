package com.ramostear.unaboot.common.util;

import com.ramostear.unaboot.common.UnaConst;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName HtmlUtils
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/9 0009 0:01
 * @Version 1.0
 **/
public class HtmlUtils {

    /**
     * 获取Html文档中图片的路径
     * @param source
     * @return
     */
    public static List<String> matchImageSrcValue(String source){
        List<String> result = new ArrayList<>();
        Pattern img_pattern = Pattern.compile(UnaConst.IMG_PATTERN_2);
        Matcher img_matcher = img_pattern.matcher(source);
        boolean isFound = img_matcher.find();
        if(isFound){
            while (isFound){
                String image_content = img_matcher.group(2);
                Pattern src_pattern = Pattern.compile(UnaConst.IMG_SRC_PATTERN);
                Matcher src_matcher = src_pattern.matcher(image_content);
                if(src_matcher.find()){
                    String src_value = src_matcher.group(3);
                    result.add(src_value);
                }
            }
        }
        return result;
    }

    /**
     * 给图片追加样式
     * @param source
     * @param styles
     * @return
     */
    public static String appendImageClass(String source,String styles){
        Assert.hasLength(source,"html content must not be empty");
        return  source.replace(UnaConst.IMG_PATTERN_1,"$1 class=\""+styles+"\"$2");
    }
}
