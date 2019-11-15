package com.ramostear.unaboot.web.config.properties;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
@Configuration
public class DataSourceProperty {

    @Value( "${spring.datasource.druid.initialSize}" )
    private int initialSize;

    @Value ( "${spring.datasource.druid.minIdle}" )
    private int minIdle;

    @Value ( "${spring.datasource.druid.maxActive}" )
    private int maxActive;

    @Value("${spring.datasource.druid.maxWait}")
    private int maxWait;

    @Value ( "${spring.datasource.druid.timeBetweenEvictionRunsMillis}" )
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.maxEvictableIdleTimeMillis}")
    private int maxEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.druid.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.druid.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.druid.testOnReturn}")
    private boolean testOnReturn;

    public DruidDataSource druidDataSource(DruidDataSource druidDataSource){
        /** 配置初始化大小、最小、最大 */
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMinIdle(minIdle);

        /** 配置获取连接等待超时的时间 */
        druidDataSource.setMaxWait(maxWait);

        /** 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 */
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        /** 配置一个连接在池中最小、最大生存的时间，单位是毫秒 */
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        druidDataSource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);

        /**
         * 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
         */
        druidDataSource.setValidationQuery(validationQuery);
        /** 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。 */
        druidDataSource.setTestWhileIdle(testWhileIdle);
        /** 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 */
        druidDataSource.setTestOnBorrow(testOnBorrow);
        /** 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 */
        druidDataSource.setTestOnReturn(testOnReturn);
        try {
            druidDataSource.addFilters("stat,wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }
}
