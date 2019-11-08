package com.ramostear.unaboot.domain.dto.support;

import com.ramostear.unaboot.common.util.BeanUtils;
import com.ramostear.unaboot.common.util.ReflectUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

public interface InputConvert<T> {

    default T convertTo(){
        ParameterizedType type = type();
        Objects.requireNonNull(type,"Can not fetch actual type because parameterized type is null");
        Class<T> t = (Class<T>)type.getActualTypeArguments()[0];
        return BeanUtils.transform(this,t);
    }

    default void update(T t){
        BeanUtils.update(this,t);
    }

    default ParameterizedType type(){
        return ReflectUtils.getParameterizedType(InputConvert.class,this.getClass());
    }

}
