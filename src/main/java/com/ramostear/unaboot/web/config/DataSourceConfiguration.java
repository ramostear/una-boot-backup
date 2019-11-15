package com.ramostear.unaboot.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.ramostear.unaboot.common.jdbc.DynamicDataSource;
import com.ramostear.unaboot.common.jdbc.support.Type;
import com.ramostear.unaboot.web.config.properties.DataSourceProperty;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
@Configuration
public class DataSourceConfiguration {

    /**
     * 主数据源配置
     * @param property      数据源配置参数
     * @return              master dataSource
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource master(DataSourceProperty property){
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return property.druidDataSource(dataSource);
    }

    /**
     * 从数据源配置
     * @param property          从数据源配置参数
     * @return                  slave dataSource
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave",name = "enabled",havingValue = "true")
    public DataSource slave(DataSourceProperty property){
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return property.druidDataSource(dataSource);
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dynamicDataSource(DataSource master, DataSource slave){
        Map<Object,Object> targetDataSource = new HashMap<>(2);
        targetDataSource.put(Type.MASTER.name(),master);
        targetDataSource.put(Type.SLAVE.name(),slave);
        return new DynamicDataSource(master,targetDataSource);
    }

    @Bean
    public DruidStatInterceptor druidStatInterceptor(){
        return new DruidStatInterceptor();
    }

    @Bean
    @Scope("prototype")
    public JdkRegexpMethodPointcut jdkRegexpMethodPointcut(){
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPatterns("com.ramostear.unaboot.*");
        return pointcut;
    }

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(DruidStatInterceptor druidStatInterceptor, JdkRegexpMethodPointcut pointcut){
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(druidStatInterceptor);
        return advisor;
    }
}
