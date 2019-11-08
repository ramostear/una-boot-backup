package com.ramostear.unaboot.common.util;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @ClassName ReflectUtils
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/9 0009 0:17
 * @Version 1.0
 **/
public class ReflectUtils {
    private ReflectUtils(){}

    @Nullable
    public static ParameterizedType getParameterizedType(@NonNull Class<?> superType, Type...genericTypes){
        Assert.notNull(superType,"Interface or super type must not be null");
        ParameterizedType type = null;
        for(Type genericType : genericTypes){
            if(genericType instanceof  ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                if(parameterizedType.getRawType().getTypeName().equals(superType.getTypeName())){
                    type = parameterizedType;
                    break;
                }
            }
        }
        return type;
    }

    @Nullable
    public static ParameterizedType getParameterizedType(@NonNull Class<?> interfaceType,Class<?> implementClz){
        Assert.notNull(interfaceType,"Interface type must not be null");
        Assert.isTrue(interfaceType.isInterface(),"The give type must be a interface");

        if(implementClz == null) return null;

        ParameterizedType type = getParameterizedType(interfaceType,implementClz.getGenericInterfaces());
        if(type != null) return type;

        Class<?> superClass = implementClz.getSuperclass();
        return getParameterizedType(interfaceType,superClass);
    }

    @Nullable
    public static ParameterizedType getParameterizedTypeBySuperClass(@NonNull Class<?>superClassType,Class<?> extensionClass){
        if(extensionClass == null) return null;
        return getParameterizedType(superClassType,extensionClass.getGenericSuperclass());
    }
}
