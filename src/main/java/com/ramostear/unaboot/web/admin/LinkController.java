package com.ramostear.unaboot.web.admin;

import com.ramostear.unaboot.common.UnaConst;
import com.ramostear.unaboot.common.exception.UnaException;
import com.ramostear.unaboot.domain.entity.Link;
import com.ramostear.unaboot.domain.param.LinkParam;
import com.ramostear.unaboot.service.LinkService;
import com.ramostear.unaboot.web.UnaController;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName LinkController
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/16 0016 16:46
 * @Version 1.0
 **/
@Slf4j
@Controller
@RequestMapping("/admin/links")
@RequiresRoles(value = {UnaConst.DEFAULT_ROLE_NAME})
public class LinkController extends UnaController {

    @Autowired
    private LinkService linkService;

    @GetMapping
    public String index(Model model){
        List<Link> linkList = linkService.listAll(new Sort(Sort.Direction.DESC,"sortNum"));
        model.addAttribute("links",linkList);
        return "/admin/link/index";
    }

    @GetMapping("/write")
    public String toWrite(){
        return "/admin/link/write";
    }

    @PostMapping("/write")
    @ResponseBody
    public ResponseEntity<Object> write(@Valid @RequestBody LinkParam linkParam, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            return badRequest("你提交的数据未通过校验");
        }
        try {
            linkService.create(linkParam.convertTo());
            return ok();
        }catch (UnaException ex){
            log.warn(ex.getMessage());
            return badRequest();
        }
    }
    @GetMapping("/{linkId:\\d+}")
    public String getBy(@PathVariable("linkId")Integer linkId,Model model){
        Link link = linkService.getById(linkId);
        model.addAttribute("link",linkService.convertToLinkVO(link));
        return "/admin/link/edit";
    }

    @PutMapping("/{linkId:\\d+}")
    @ResponseBody
    public ResponseEntity<Object> updateBy(@Valid @RequestBody LinkParam linkParam,
                                           BindingResult bindingResult,
                                           @PathVariable("linkId")Integer linkId){
        if(bindingResult.hasFieldErrors()){
            return badRequest("数据未通过校验");
        }
        try {
            Link link = linkService.getById(linkId);
            linkParam.update(link);
            linkService.update(link);
            return ok();
        }catch (UnaException ex){
            log.warn(ex.getMessage());
            return badRequest();
        }
    }

    @DeleteMapping("/{linkId:\\d+}")
    @ResponseBody
    public ResponseEntity<Object> deleteBy(@PathVariable("linkId")Integer linkId){
        try {
            linkService.removeById(linkId);
            return ok();
        }catch (UnaException ex){
            log.warn(ex.getMessage());
            return badRequest();
        }
    }
}
