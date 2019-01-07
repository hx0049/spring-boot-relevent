package com.hx.conf;
 
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
 
/**
 * Created by Administrator on 2017/8/11.
 *
 * 数据源的配置
 *
 */
@Configuration
public class DataSourceConfig {
 
 
    @Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.primary")
    public DataSource primaryDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "secondaryDataSource")
    @Qualifier("secondaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.secondary")
    public DataSource secondaryDataSource() {
        return new DruidDataSource();
    }


    @Bean(name = "primaryJpaProperties")
    @Qualifier("primaryJpaProperties")
    @Primary
    @ConfigurationProperties(prefix="spring.jpa.primary")
    public JpaProperties primaryJpaProperties() {
        return new JpaProperties();
    }

    @Bean(name = "secondaryJpaProperties")
    @Qualifier("secondaryJpaProperties")
    @ConfigurationProperties(prefix="spring.jpa.secondary")
    public JpaProperties secondaryJpaProperties() {
        return new JpaProperties();
    }
 
 
 
}
