package com.ramostear.unaboot.common.jdbc;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName DynamicDataSourceContextHolder
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/8 0008 23:12
 * @Version 1.0
 **/
@Slf4j
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 切换数据源
     * @param datasource
     */
    public static void setDataSource(String datasource){
        log.info("将数据源切换到{}数据源上",datasource);
        CONTEXT_HOLDER.set(datasource);
    }

    /**
     * 获取当前数据源
     * @return
     */
    public static String getDataSource(){
        return CONTEXT_HOLDER.get();
    }
}
