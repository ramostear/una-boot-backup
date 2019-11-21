package com.ramostear.unaboot.web.admin;

import com.ramostear.unaboot.common.UnaConst;
import com.ramostear.unaboot.common.exception.UnaException;
import com.ramostear.unaboot.domain.entity.Tag;
import com.ramostear.unaboot.service.TagService;
import com.ramostear.unaboot.web.UnaController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
@Slf4j
@Controller
@RequestMapping("/admin/tags")
@RequiresRoles(value = {UnaConst.DEFAULT_ROLE_NAME})
public class TagController extends UnaController {

    @Autowired
    private TagService tagService;


    @GetMapping
    public String tags(@RequestParam(name = "selected",required = false)String selected, Model model){
        if(StringUtils.isNotBlank(selected)){
            List<Integer> tagIdList = Arrays.stream(selected.split(","))
                    .map(idStr->Integer.valueOf(idStr))
                    .collect(Collectors.toList());
            List<Tag> tagList = tagService.listAll();
            List<Tag> unselect = new ArrayList<>(),select = new ArrayList<>();
            if(!CollectionUtils.isEmpty(tagList)){
                tagList.forEach(tag->{
                    if(tagIdList.contains(tag.getId())){
                        unselect.add(tag);
                        select.add(tag);
                    }
                });
                tagList.removeAll(unselect);
            }
            model.addAttribute("tags",tagList)
                    .addAttribute("selectedTags",select);
        }else{
            model.addAttribute("tags",tagService.listAll())
                    .addAttribute("selectedTags",null);
        }
        return "/admin/tag/index";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> createBy(@RequestParam("name")String name,@RequestParam("slug")String slug){
        try {
            Tag tag = new Tag();
            tag.setName(name);
            tag.setSlug(slug);
            tagService.createBy(tag);
            return new ResponseEntity<>(tag, HttpStatus.OK);
        }catch (UnaException ex){
            log.warn("create tag error:[{}]",ex.getMessage());
            return badRequest();
        }
    }
}
