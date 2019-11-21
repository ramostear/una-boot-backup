package com.ramostear.unaboot.web.admin;

import com.ramostear.unaboot.common.UnaConst;
import com.ramostear.unaboot.common.exception.UnaException;
import com.ramostear.unaboot.common.factory.support.Qiniu;
import com.ramostear.unaboot.common.util.CDNUtils;
import com.ramostear.unaboot.domain.dto.GitalkDTO;
import com.ramostear.unaboot.domain.entity.Setting;
import com.ramostear.unaboot.service.SettingService;
import com.ramostear.unaboot.web.UnaController;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ ClassName SettingController
 * @ Description TODO
 * @ Author ramostear
 * @ Date 2019/11/13 0013 3:18
 * @ Version 1.0
 **/
@Slf4j
@Controller
@RequestMapping("/admin/settings")
@RequiresRoles(value = {UnaConst.DEFAULT_ROLE_NAME})
public class SettingController extends UnaController {

    private final SettingService settingService;

    private final ServletContext servletContext;

    public SettingController(SettingService settingService,ServletContext servletContext){
        this.settingService = settingService;
        this.servletContext = servletContext;
    }

    @GetMapping("/general")
    public String general(Model model){
        model.addAttribute("general",settingService.convertToMap());
        return "/admin/setting/general";
    }

    @ResponseBody
    @PostMapping("/general")
    public ResponseEntity<Object> general(HttpServletRequest request){
        return updateSetting(request);
    }


    @GetMapping("/talk")
    public String gitalk(Model model){
        model.addAttribute("gitalk",settingService.getGitalk());
        return "/admin/setting/talk";
    }

    @ResponseBody
    @PostMapping("/talk")
    public ResponseEntity<Object> gitalk(HttpServletRequest request){
        return updateSetting(request);
    }

    @GetMapping("/cdn")
    public String cdn(Model model){
        model.addAttribute("cdn", CDNUtils.getQiniu());
        return "/admin/setting/cdn";
    }

    @ResponseBody
    @PostMapping("/cdn")
    public ResponseEntity<Object> cdn(Qiniu qiniu){
        boolean result = CDNUtils.setQiniu(qiniu);
        if(result){
            return ok();
        }else {
            return badRequest();
        }
    }

    @GetMapping("/druid")
    public String druid(){
        return "/admin/setting/druid";
    }

    private ResponseEntity<Object> updateSetting(HttpServletRequest request){
        Map<String,String[]> params = request.getParameterMap();
        List<Setting> items = new ArrayList<>();
        params.forEach((key,value)->{
            items.add(Setting.builder().key(key).value(value[0]).build());
        });
        try {
            settingService.update(items);
            Map<String,Setting> settings =settingService.convertToMap();
            Set<String> keySet = settings.keySet();
            keySet.forEach(key->{
                log.info("general setting key:[{}], value:[{}]",key,settings.get(key).getValue());
                servletContext.setAttribute(key,settings.get(key).getValue());
            });
            return ok();
        }catch (UnaException ex){
            return badRequest();
        }
    }
}
