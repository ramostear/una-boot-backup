package com.ramostear.unaboot.domain.dto.support;

import com.ramostear.unaboot.common.util.BeanUtils;
import org.springframework.lang.NonNull;

/**
 * @ClassName OutputConvert
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/9 0009 0:21
 * @Version 1.0
 **/
public interface OutputConvert<X extends OutputConvert<X,Y>,Y> {

    default <T extends X> T convertFrom(@NonNull Y y){
        BeanUtils.update(y,this);
        return (T)this;
    }
}
