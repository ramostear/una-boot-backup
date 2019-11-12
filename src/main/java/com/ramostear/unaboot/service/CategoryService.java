package com.ramostear.unaboot.service;

import com.ramostear.unaboot.domain.dto.CategoryDTO;
import com.ramostear.unaboot.domain.entity.Category;
import com.ramostear.unaboot.domain.vo.CategoryVO;
import com.ramostear.unaboot.service.support.IUnaService;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;

public interface CategoryService extends IUnaService<Category,Integer> {

    List<CategoryVO> tree(@NonNull Sort sort);

    @NonNull
    Category getBySlug(@NonNull String slug);

    @NonNull
    Category getBySlugOfNonNull(String slug);

    @NonNull
    Category getByName(@NonNull String name);

    void removeCategoryAndRelationById(Integer categoryId);

    @NonNull
    CategoryDTO convertTo(@NonNull Category category);

    List<CategoryDTO> convertTo(@NonNull Collection<Category> categories);
}
