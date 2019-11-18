package com.ramostear.unaboot.web.admin;

import com.ramostear.unaboot.common.UnaConst;
import com.ramostear.unaboot.domain.dto.CategoryDTO;
import com.ramostear.unaboot.domain.entity.Category;
import com.ramostear.unaboot.domain.param.CategoryParam;
import com.ramostear.unaboot.domain.vo.CategoryVO;
import com.ramostear.unaboot.service.CategoryService;
import com.ramostear.unaboot.service.ThemeService;
import com.ramostear.unaboot.web.UnaController;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName CategoryController
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/13 0013 2:04
 * @Version 1.0
 **/
@Controller
@RequestMapping("/admin/categories")
@RequiresRoles(value = {UnaConst.DEFAULT_ROLE_NAME})
public class CategoryController extends UnaController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ThemeService themeService;

    @GetMapping
    public String index(){
        return "/admin/category/index";
    }

    @ResponseBody
    @GetMapping("/{categoryId:\\d+}")
    public CategoryDTO getById(@PathVariable("categoryId")Integer categoryId){
        CategoryDTO data = categoryService.convertTo(categoryService.getById(categoryId));
        setParentName(data);
        return data;
    }

    @ResponseBody
    @GetMapping("/tree")
    public List<CategoryVO> tree(){
        return categoryService.tree(new Sort(Sort.Direction.ASC,"sortNum"));
    }

    @ResponseBody
    @PutMapping("/{categoryId:\\d+}")
    public CategoryDTO update(@PathVariable("categoryId")Integer categoryId,
                              @RequestBody @Valid CategoryParam param){
        Category category = categoryService.getById(categoryId);
        param.update(category);
        return categoryService.convertTo(categoryService.update(category));
    }

    @ResponseBody
    @DeleteMapping("/{categoryId:\\d+}")
    public void delete(@PathVariable("categoryId")Integer categoryId){
        categoryService.removeCategoryAndRelationById(categoryId);
    }

    @ResponseBody
    @PostMapping(produces = "application/json;utf-8")
    public CategoryDTO create(@RequestBody @Valid CategoryParam valid){
        return categoryService.convertTo(categoryService.create(valid.convertTo()));
    }

    @GetMapping("/{parentId:\\d+}/new")
    public String toCreate(@PathVariable(name = "parentId")Integer parentId, Model model){
        Category parent;
        if(parentId <= 0){
            parent = new Category();
            parent.setId(0);
            parent.setName("root");
        }else{
            parent = categoryService.getById(parentId);
        }
        model.addAttribute("parent",parent)
             .addAttribute("themes",themeService.htmlFiles());
        return "/admin/category/create";
    }

    @GetMapping("/themes")
    public String themes(Model model){
        model.addAttribute("themes",themeService.htmlFiles());
        return "/admin/category/themes";
    }

    /*
     * 设置父节点名称
     * @param data  节点数据
     */
    private void setParentName(CategoryDTO data){
        if(data.getParentId() == 0){
            data.setParentName("root");
        }else {
            data.setParentName(categoryService.getById(data.getParentId()).getName());
        }
    }
}
