package com.ramostear.unaboot.web.admin;

import com.ramostear.unaboot.common.UnaConst;
import com.ramostear.unaboot.common.exception.UnaException;
import com.ramostear.unaboot.common.util.DateUtils;
import com.ramostear.unaboot.common.util.RandomUtils;
import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.domain.param.PostParam;
import com.ramostear.unaboot.domain.param.PostQuery;
import com.ramostear.unaboot.service.CategoryService;
import com.ramostear.unaboot.service.PostService;
import com.ramostear.unaboot.service.ThemeService;
import com.ramostear.unaboot.web.UnaController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
@Slf4j
@Controller
@RequestMapping("/admin/posts")
@RequiresRoles(value = {UnaConst.DEFAULT_ROLE_NAME})
public class PostController extends UnaController {

    @Autowired
    private PostService postService;
    @Autowired
    private ThemeService themeService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String index(PostQuery postQuery, Model model){
        Page<Post> postPage = postService.pageBy(postQuery,pageByDesc("updateTime"));
        model.addAttribute("data",postService.convertToPostListVO(postPage))
             .addAttribute("query",postQuery)
             .addAttribute("urlParam",generalUrlParam(postQuery))
             .addAttribute("categories",categoryService.listAll());
        return "/admin/post/index";
    }


    @GetMapping("/write")
    public String toWrite(Model model){
        model.addAttribute("slug",generalSlug());
        return "/admin/post/write";
    }

    @PostMapping("/write")
    @ResponseBody
    public ResponseEntity<Object> write(@Valid @RequestBody PostParam postParam, BindingResult bindingResult,
                                        @RequestParam(value = "autoSave",required = false,defaultValue = "0")int autoSave){
        if(bindingResult.hasFieldErrors()){
            return badRequest("你提交的数据共有"+bindingResult.getFieldErrorCount()+"处错误，请检查更改后再提交");
        }
        try {
            Post post = postParam.convertTo();
            postService.createBy(post,postParam.convertToTagIds(),postParam.getCategoryId(),autoSave==1);
            return ok("文章已经保存");
        }catch (UnaException ex){
            log.warn("服务器异常:[{}]",ex.getMessage());
            return badRequest("服务器异常，请稍后再试");
        }

    }

    /**
     * 获取模板文件
     * @param model
     * @return
     */
    @GetMapping("/tpls")
    public String getTpls(Model model){
        model.addAttribute("tpls",themeService.htmlFiles());
        return "/admin/post/tpl";
    }

    @GetMapping("/categories")
    public String getCategories(Model model){
        model.addAttribute("categories",categoryService.listAll());
        return "/admin/post/category";
    }

    @GetMapping("/upload")
    public String uploadImag(){
        return "/admin/post/upload";
    }

    @GetMapping("/{postId:\\d+}")
    public String getBy(@PathVariable("postId")Integer postId,Model model){
        Post post = postService.getById(postId);
        model.addAttribute("post",postService.convertToPostVO(post));
        return "/admin/post/edit";
    }

    @PutMapping("/{postId:\\d+}")
    @ResponseBody
    public ResponseEntity<Object> updateBy(@Valid @RequestBody PostParam postParam,BindingResult bindingResult,
                                           @PathVariable("postId")Integer postId,
                                           @RequestParam(value = "autoSave",required = false,defaultValue = "0")int autoSave){
        if(bindingResult.hasFieldErrors()){
            return badRequest("你提交的数据共有 "+bindingResult.getFieldErrorCount()+" 处未通过校验,请检查并更改后再提交");
        }
        try {
            Post post = postService.getById(postId);
            postParam.update(post);
            postService.updateBy(post,postParam.convertToTagIds(),postParam.getCategoryId(),autoSave==1);
            return ok("文章已经更新");
        }catch (UnaException ex){
            log.warn(ex.getMessage());
            return badRequest("服务器异常");
        }
    }

    @PutMapping("/{postId:\\d+}/status/{status}")
    public ResponseEntity<Object> updateStatus(@PathVariable("postId")Integer postId,@PathVariable("status")Integer status){
        if(status == null || status<0 || status >1){
            return badRequest("请求参数错误");
        }
        try {
            Post post = postService.getById(postId);
            post.setStatus(status);
            postService.update(post);
            return ok();
        }catch (UnaException ex){
            log.warn(ex.getMessage());
            return badRequest();
        }
    }

    @DeleteMapping("/{postId:\\d+}")
    @ResponseBody
    public ResponseEntity<Object> deleteBy(@PathVariable("postId")Integer postId){
        try {
            postService.removeById(postId);
            return ok();
        }catch (UnaException ex){
            log.warn(ex.getMessage());
            return badRequest();
        }
    }

    /**
     * 生成分页查询的url参数
     * @param postQuery     查询参数
     * @return              URL参数
     */
    @NonNull
    private String generalUrlParam(@NonNull PostQuery postQuery){
        if(postQuery == null){
            return null;
        }
        String urlParam = "";
        if(postQuery.getStatus() != null && (postQuery.getStatus() == 0 || postQuery.getStatus() == 1)){
            urlParam+="&status="+postQuery.getStatus();
        }
        if(StringUtils.isNotBlank(postQuery.getKeyword())){
            urlParam+="&keywords="+postQuery.getKeyword();
        }
        if(postQuery.getCategoryId() != null && postQuery.getCategoryId() >0){
            urlParam+="&categoryId="+postQuery.getCategoryId();
        }
        if(urlParam.length()<=1){
            return null;
        }else{
            return "?"+urlParam.substring(1);
        }
    }

    /**
     * 随机生成slug
     * @return      String
     */
    private String generalSlug(){
        return new SimpleDateFormat("yyyy/mm/dd")
                .format(DateUtils.now())
                +"/"
                + RandomUtils.next(8)
                +".html";
    }
}
