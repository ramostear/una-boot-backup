package com.ramostear.unaboot.service;

import com.ramostear.unaboot.domain.dto.GitalkDTO;
import com.ramostear.unaboot.domain.entity.Setting;
import com.ramostear.unaboot.service.support.IUnaService;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Map;

public interface SettingService extends IUnaService<Setting,Integer> {

    Map<String,Setting> convertToMap();

    void update(@NonNull Collection<Setting> settings);

    @NonNull
    String getValue(@NonNull String key);

    GitalkDTO getGitalk();
}
