package com.qf.dataconfig;

import com.qf.datasource.DataSourceDb1;
import com.qf.datasource.DataSourceDb2;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/24 21:02
 */
@Configuration
public class MyBatisConfig {

    @Autowired
    private DataSourceDb1 dataSourceDb1;
    @Autowired
    private DataSourceDb2 dataSourceDb2;

    @Value("${mybatis-plus.mapper-locations}")
    private String mapperLocation;

    @PostConstruct
    public void init(){
        System.out.println("数据源1："+dataSourceDb1);
        System.out.println("数据源2："+dataSourceDb2);
    }

    //配置动态数据源
    @Bean
    public DynamicDataSource getDataSource(){
        Map<Object,Object> map = new HashMap<>();
        map.put(dataSourceDb1.getKeyword(),dataSourceDb1.getDataSource());
        map.put(dataSourceDb2.getKeyword(),dataSourceDb2.getDataSource());
        System.out.println(map);

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(dataSourceDb1.getDataSource());
        dynamicDataSource.setTargetDataSources(map);

        return dynamicDataSource;
    }

    //配置SqlSessionFactoryBean
    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(DynamicDataSource getDataSource){

        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(getDataSource);

        try {
            sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sessionFactoryBean;
    }


}
