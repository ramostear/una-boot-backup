package com.ramostear.unaboot.repository;

import com.ramostear.unaboot.domain.entity.Theme;
import com.ramostear.unaboot.repository.support.UnaRepository;

import java.util.List;

public interface ThemeRepository extends UnaRepository<Theme,Integer> {

    /**
     * 根据主题状态查询
     * @param status        主题状态：0-禁用，1-启用
     * @return              List<Theme>
     */
    List<Theme> findAllByStatus(Integer status);

    /**
     * 根据主题名称查询
     * @param name      主题名
     * @return          Theme
     */
    Theme findByName(String name);

}
