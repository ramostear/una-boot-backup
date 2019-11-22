package com.ramostear.unaboot.web.config;

import com.ramostear.unaboot.domain.entity.Setting;
import com.ramostear.unaboot.domain.entity.Theme;
import com.ramostear.unaboot.service.SettingService;
import com.ramostear.unaboot.service.ThemeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
@Service
@Slf4j
public class LauncherConfiguration implements ApplicationRunner, Ordered, ServletContextAware {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private SettingService settingService;

    @Autowired
    private ThemeService themeService;

    @Override
    public void run(ApplicationArguments args) {
        Map<String, Setting> settingMap = settingService.convertToMap();
        ExecutorService executorService = new ThreadPoolExecutor(1,1,0,
                TimeUnit.SECONDS,new ArrayBlockingQueue<>(1),
                new ThreadPoolExecutor.DiscardPolicy());
        executorService.execute(()->{
            Set<String> keySet = settingMap.keySet();
            keySet.forEach((String key)->{
                servletContext.setAttribute(key,settingMap.get(key).getValue());
                log.info("general setting key:[{}], value:[{}]",key,settingMap.get(key).getValue());
            });
            Theme activeTheme = themeService.used().get(0);
            if(activeTheme != null){
                servletContext.setAttribute("theme",activeTheme);
                log.info("active theme :[{}]",activeTheme);
            }
        });
        executorService.shutdown();
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
