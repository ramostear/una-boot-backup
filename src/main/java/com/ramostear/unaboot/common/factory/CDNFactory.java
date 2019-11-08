package com.ramostear.unaboot.common.factory;

/**
 * @ClassName CDNFactory
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/8 0008 23:27
 * @Version 1.0
 **/
public abstract class CDNFactory {

    abstract public CDN build();

    public CDN get(){return build();}
}

