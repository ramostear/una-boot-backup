package com.ramostear.unaboot.repository;

import com.ramostear.unaboot.domain.entity.Setting;
import com.ramostear.unaboot.repository.support.UnaRepository;
import org.springframework.lang.NonNull;

public interface SettingRepository extends UnaRepository<Setting,Integer> {

    @NonNull
    Setting findByKey(@NonNull String key);
}
