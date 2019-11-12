package com.ramostear.unaboot.service.impl;

import com.ramostear.unaboot.domain.entity.Setting;
import com.ramostear.unaboot.repository.SettingRepository;
import com.ramostear.unaboot.service.SettingService;
import com.ramostear.unaboot.service.support.UnaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ ClassName SettingServiceImpl
 * @ Description TODO
 * @ Author ramostear
 * @ Date 2019/11/13 0013 3:06
 * @ Version 1.0
 **/
@Slf4j
@Service("settingService")
public class SettingServiceImpl extends UnaService<Setting,Integer> implements SettingService {

    private final SettingRepository settingRepository;

    public SettingServiceImpl(SettingRepository settingRepository) {
        super(settingRepository);
        this.settingRepository = settingRepository;
    }

    @Override
    public Map<String, Setting> convertToMap() {
        List<Setting> settings = listAll();
        if(CollectionUtils.isEmpty(settings)){
            return Collections.emptyMap();
        }
        return settings.stream()
                .collect(Collectors.toMap(Setting::getKey,setting->setting));
    }

    @Override
    @Transactional
    public void update(Collection<Setting> settings) {
        if(CollectionUtils.isEmpty(settings)){
            return;
        }
        List<Setting> items = new ArrayList<>();
        settings.forEach(setting -> {
            Setting item = settingRepository.findByKey(setting.getKey());
            if(item != null){
                item.setValue(setting.getValue());
            }else{
                item = Setting.builder()
                        .key(setting.getKey())
                        .value(setting.getValue())
                        .build();
            }
            items.add(item);
        });
        updateInBatch(items);
    }

    @Override
    public String getValue(String key) {
        Setting setting = settingRepository.findByKey(key);
        return setting != null?setting.getValue():"";
    }
}
