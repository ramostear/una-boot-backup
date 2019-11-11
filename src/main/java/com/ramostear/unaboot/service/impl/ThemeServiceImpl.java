package com.ramostear.unaboot.service.impl;

import com.ramostear.unaboot.common.UnaConst;
import com.ramostear.unaboot.domain.entity.Theme;
import com.ramostear.unaboot.repository.ThemeRepository;
import com.ramostear.unaboot.service.ThemeService;
import com.ramostear.unaboot.service.support.UnaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @ ClassName ThemeServiceImpl
 * @ Description 主题业务接口实现类
 * @ Author ramostear
 * @ Date 2019/11/12 0012 5:26
 * @ Version 1.0
 **/
@Slf4j
@Service("themeService")
public class ThemeServiceImpl extends UnaService<Theme,Integer> implements ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeServiceImpl(ThemeRepository themeRepository) {
        super(themeRepository);
        this.themeRepository = themeRepository;
    }

    @Override
    @Transactional
    public Theme install(Theme theme) {
        Assert.notNull(theme,"theme must not be null.");
        return themeRepository.save(theme);
    }

    @Override
    public Theme active(String name) {
        List<Theme> activeThemes = this.used();
        if(activeThemes != null){
            for(Theme theme:activeThemes){
                try {
                    FolderKit.remove(new File(
                            ResourceUtils.getURL("classpath").getPath()
                                    +UnaConst.FILE_SEPARATOR
                            +"templates"
                            +UnaConst.FILE_SEPARATOR
                            +"themes"
                            +UnaConst.FILE_SEPARATOR
                            +theme.getName()
                            ));
                }catch (FileNotFoundException ex){
                  log.error("file not found exception.");
                  return null;
                }
                theme.setStatus(0);
                themeRepository.save(theme);
            }
        }
        Theme _theme = themeRepository.findByName(name);
        if(_theme != null){
            try {
                FolderKit.copy(name);
            }catch (IOException ex){
                log.error("copy theme file error");
                return null;
            }
            _theme.setStatus(1);
            themeRepository.save(_theme);
            return _theme;
        }else{
            return null;
        }
    }

    @Override
    public List<Theme> used() {
        return themeRepository.findAllByStatus(1);
    }

    @Override
    public List<Theme> unUsed() {
        return themeRepository.findAllByStatus(0);
    }

    @Override
    public List<String> htmlFiles() {
        return FolderKit.htmlFiles(themeRepository.findAllByStatus(1).get(0).getName());
    }
}
