package com.ld.sso.core.dbconfigure;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;



@Configuration
//扫描 Mapper 接口并容器管理
@MapperScan(basePackages = RoomRentDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "roomrentSqlSessionFactory")
public class RoomRentDataSourceConfig {

 // 精确到  目录，以便跟其他数据源隔离
 static final String PACKAGE = "com.ld.sso.roomrent.mapper";
 static final String MAPPER_LOCATION = "classpath:roomrent_mapper/*.xml";

 @Value("${roomrent.datasource.url}")
 private String url;

 @Value("${roomrent.datasource.username}")
 private String user;

 @Value("${roomrent.datasource.password}")
 private String password;

 @Value("${mysql.datasource.driverClassName}")
 private String driverClass;

 @Bean(name = "roomrentDataSource")
// @Primary
 public DataSource roomrentDataSource() {
     DruidDataSource dataSource = new DruidDataSource();
     dataSource.setDriverClassName(driverClass);
     dataSource.setUrl(url);
     dataSource.setUsername(user);
     dataSource.setPassword(password);
     return dataSource;
 }

 @Bean(name = "roomrentTransactionManager")
 //@Primary
 public DataSourceTransactionManager roomrentTransactionManager() {
     return new DataSourceTransactionManager(roomrentDataSource());
 }

 @Bean(name = "roomrentSqlSessionFactory")
 //@Primary
 public SqlSessionFactory roomrentSqlSessionFactory(@Qualifier("roomrentDataSource") DataSource roomrentDataSource)
         throws Exception {
     final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
     sessionFactory.setDataSource(roomrentDataSource);
     sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
             .getResources(RoomRentDataSourceConfig.MAPPER_LOCATION));
     return sessionFactory.getObject();
 }
}
