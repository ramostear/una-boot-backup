package com.ramostear.unaboot.web;

import com.ramostear.unaboot.common.util.CookieUtils;
import com.ramostear.unaboot.common.util.EncryptUtils;
import com.ramostear.unaboot.domain.entity.Category;
import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.domain.entity.Tag;
import com.ramostear.unaboot.domain.entity.Theme;
import com.ramostear.unaboot.domain.vo.PostVO;
import com.ramostear.unaboot.service.CategoryService;
import com.ramostear.unaboot.service.PostService;
import com.ramostear.unaboot.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName FrontController
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/22 0022 3:28
 * @Version 1.0
 **/
@Slf4j
@Controller
public class FrontController extends UnaController{

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostService postService;

    /**
     * 网站主页
     * @return
     */
    @GetMapping(value = {"/","/index","/index.html","/home.html"})
    public String index(){
        return view("index.html");
    }

    /**
     * 栏目页面
     * @param slug      栏目访问名称
     * @param offset    是否存在数据分页，默认为第一页
     * @param model     回显数据模型
     * @return          栏目模板页面
     */
    @GetMapping("/{slug}")
    public String category(@PathVariable("slug")String slug,
                           @RequestParam(name = "offset",defaultValue = "1")Integer offset,
                           Model model){
        Category category = categoryService.getBySlug(slug);
        if(category != null){
            model.addAttribute("category",category)
                 .addAttribute("slug",slug)
                 .addAttribute("offset",offset);
            return view(category.getTemplate());
        }else{
            return view("index.html");
        }
    }

    /**
     * 访问标签页面，如果没有该标签，返回首页
     * @param slug      标签地址名称
     * @param offset    默认的文章分页起始位置
     * @param model     回显数据
     * @return          tags.html
     */
    @GetMapping("/tags/{slug}")
    public String tags(@PathVariable("slug")String slug,@RequestParam(name = "offset",defaultValue = "1")Integer offset, Model model){
        Tag tag = tagService.findBySlug(slug);
        if(tag != null){
            model.addAttribute("tag",tag)
                 .addAttribute("slug",slug)
                 .addAttribute("offset",offset);
            return view("tags.html");
        }else{
            return view("index.html");
        }
    }

    @GetMapping("/post/**")
    public String post(HttpServletRequest request,Model model){
        String uri = request.getRequestURI();
        if(StringUtils.isBlank(uri)){
            return redirect("/");
        }
        String slug = uri.substring(uri.indexOf("post/")+5);
        if(StringUtils.isBlank(slug)){
            return redirect("/");
        }
        log.info("post slug:[{}]",slug);
        Post post = postService.findBySlug(slug);
        if(post == null || post.getStatus() == 0){
            return redirect("/");
        }
        String cookieValue = CookieUtils.read("ubp"+post.getId());
        if(cookieValue == null || cookieValue.trim().equals("")){
            CookieUtils.write("ubp"+post.getId(),EncryptUtils.encrypt("post_"+post.getId()),60);
            log.info("post cookie,key:[{}],value:[{}]","ubp"+post.getId(),EncryptUtils.encrypt("post_"+post.getId()));
            post = postService.identityVisits(post.getId());
        }
        PostVO postVO = postService.convertToPostVO(post);
        model.addAttribute("post",postVO);
        return view(post.getTemplate());
    }

    private String view(String template){
        Theme theme = (Theme) servletContext.getAttribute("theme");
        return "/themes/"+theme.getName()+"/"+template.substring(0,template.indexOf("."));
    }
}
