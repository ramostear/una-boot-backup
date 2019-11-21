package com.ramostear.unaboot.domain.param;

import com.ramostear.unaboot.common.util.DateUtils;
import com.ramostear.unaboot.common.util.RandomUtils;
import com.ramostear.unaboot.domain.dto.support.InputConvert;
import com.ramostear.unaboot.domain.entity.Post;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Ramostear on 2019/11/13 0013.
 */
@Data
public class PostParam implements InputConvert<Post> {

    @NotBlank(message = "文章标题不能为空")
    @Size(max = 68,min = 8,message = "文章标题长度必须在{min}和{max}之间")
    private String title;

    private Integer status = 0;

    @Size(max = 64,message = "slug长度不能超过{max}")
    private String slug;

    @NotBlank(message = "markdown文本不能为空")
    private String markdown;

    @NotBlank(message = "html文本不能为空")
    private String html;

    @NotBlank(message = "文章摘要不能为空")
    @Size(max = 120,message = "文章摘要长度不能超过{max}")
    private String summary;

    private String thumb;

    private Boolean allowChat = false;

    @NotBlank(message = "文章的渲染模板不能为空")
    private String template;

    private Boolean allowTop = false;

    private Boolean allowFavor = false;

    private Boolean original = true;

    private String url;

    @NotBlank(message = "文章作者不能为空")
    private String author;

    @Size(max = 64,message = "文章关键词长度不能超过{max}")
    private String keywords;

    @Size(max = 120,message = "文章描述信息长度不能超过{max}")
    private String description;

    private String projectUrl;

    private Date createTime;

    private String tagIds;

    @NotNull(message = "文章栏目编号不能为空")
    private Integer categoryId;

    /**
     * 将标签编号字符串(使用“,”分割的字符串)转换为整型集合
     * @return  Set<Integer>
     */
    public Set<Integer> convertToTagIds(){
        if(StringUtils.isBlank(tagIds)){
            return Collections.emptySet();
        }
        return Arrays.stream(tagIds.split(","))
                .map(idStr->Integer.valueOf(idStr))
                .collect(Collectors.toSet());
    }

    @Override
    public Post convertTo() {
        if(StringUtils.isBlank(slug)){
            slug = generalSlug();
        }
        createTime = DateUtils.now();
        Post post = InputConvert.super.convertTo();
        return post;
    }

    /*
     * 生成文章的访问路径
     */
    private String generalSlug(){
        return new SimpleDateFormat("yyyy/MM/dd")
                .format(DateUtils.now())
                +"/"+ RandomUtils.next(8)
                +".html";
    }
}
