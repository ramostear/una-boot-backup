package com.ramostear.unaboot.service;

import com.ramostear.unaboot.domain.entity.Theme;
import com.ramostear.unaboot.service.support.IUnaService;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ThemeService extends IUnaService<Theme,Integer> {

    /**
     * 安装新主题
     * @param theme     主题
     * @return          theme
     */
    @NonNull
    Theme install(@NonNull Theme theme);

    /**
     * 根据主题名激活主题
     * @param name          主题名称
     * @return              Theme
     */
    @NonNull
    Theme active(@NonNull String name);

    boolean refresh(@NonNull String name);
    /**
     * 已启用主题
     * @return
     */
    List<Theme> used();

    /**
     * 未启用主题
     * @return
     */
    List<Theme> unUsed();

    /**
     * 已启用的主题下的.html模板文件
     * @return
     */
    List<String> htmlFiles();
}
