package com.ramostear.unaboot.web.admin;

import com.ramostear.unaboot.common.UnaConst;
import com.ramostear.unaboot.common.exception.UnaException;
import com.ramostear.unaboot.common.util.BreadCrumbUtils;
import com.ramostear.unaboot.domain.dto.PathDTO;
import com.ramostear.unaboot.domain.entity.Theme;
import com.ramostear.unaboot.domain.vo.FolderVO;
import com.ramostear.unaboot.service.ThemeService;
import com.ramostear.unaboot.service.support.FolderKit;
import com.ramostear.unaboot.service.support.ThemeKit;
import com.ramostear.unaboot.web.UnaController;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @ ClassName ThemeController
 * @ Description 主题控制器
 * @ Author ramostear
 * @ Date 2019/11/12 0012 18:14
 * @ Version 1.0
 **/
@Slf4j
@Controller
@RequestMapping("/admin/themes")
@RequiresRoles(value = {UnaConst.DEFAULT_ROLE_NAME})
public class ThemeController extends UnaController {

    @Autowired
    private ThemeService themeService;

    @GetMapping
    public String index(Model model){
        Page<Theme> themePage = themeService.listAll(pageByDesc("createTime",12));
        model.addAttribute("themes",themePage);
        return "/admin/theme/index";
    }

    @GetMapping("/install")
    public String install(){
        return "/admin/theme/install";
    }

    @ResponseBody
    @PostMapping("/install")
    public ResponseEntity<Object> install(@RequestParam("theme")CommonsMultipartFile file, HttpServletRequest request){
        try {
            themeService.install(ThemeKit.compress(request,file));
            System.gc();
            return ok();
        }catch (Exception ex){
            log.warn("install theme error:{}",ex.getMessage());
            return badRequest();
        }
    }

    @GetMapping("/folders")
    public String folders(@RequestParam("path")String path,Model model){
        try{
            List<FolderVO> folders = FolderKit.files(path);
            model.addAttribute("folders",folders);
        }catch (UnaException ex){
            log.warn("load folder files error:{}",ex.getMessage());
            model.addAttribute("folders",null);
        }
        model.addAttribute("breadcrumbs", BreadCrumbUtils.breadcrumb(path));
        return "/admin/theme/folders";
    }

    @GetMapping("/folders/img")
    public String image(@RequestParam("path")String path,Model model){
        model.addAttribute("path",path);
        return "/admin/theme/image";
    }

    @GetMapping("/folders/reader")
    public String reader(@RequestParam("path")String path,Model model){
        model.addAttribute("path",path);
        return "/admin/theme/reader";
    }

    @ResponseBody
    @PostMapping("/folders/reader")
    public String reader(@RequestParam("path")String path){
        try{
            return FolderKit.reader(path);
        }catch (Exception ex){
            log.warn(ex.getMessage());
            return "";
        }
    }

    @ResponseBody
    @PostMapping("/folders/writer")
    public ResponseEntity<Object> writer(@RequestParam("path")String path,@RequestParam("text")String text){
        try {
            FolderKit.writer(path,text);
            return ok();
        }catch (IOException ex){
            log.warn(ex.getMessage());
            return badRequest();
        }
    }

    @ResponseBody
    @PostMapping(value = "/folders/new",params = {"type=file"})
    public ResponseEntity<Object> newFile(@RequestParam("path")String path){
        try {
            FolderKit.file(path);
            return ok();
        }catch (IOException ex){
            log.warn(ex.getMessage());
            return badRequest();
        }
    }

    @ResponseBody
    @PostMapping(value = "/folders/new",params = {"type=folder"})
    public ResponseEntity<Object> newFolder(@RequestParam("path")String path){
        try {
            FolderKit.folder(path);
            return ok();
        }catch (Exception ex){
            log.warn(ex.getMessage());
            return badRequest();
        }
    }

    @ResponseBody
    @DeleteMapping("/folders")
    public ResponseEntity<Object> remove(@RequestBody PathDTO pathDTO){
        boolean result = FolderKit.remove(pathDTO.getPath());
        if(result){
            return ok();
        }else{
            return badRequest();
        }
    }

    @ResponseBody
    @PostMapping("/active/{theme}")
    public ResponseEntity<Object> active(@PathVariable("theme")String theme){
        Theme result = themeService.active(theme);
        if(result != null){
            return ok();
        }else{
            return badRequest();
        }
    }
}
