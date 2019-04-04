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
@MapperScan(basePackages = SSODataSourceConfig.PACKAGE, sqlSessionFactoryRef = "ssoSqlSessionFactory")
public class SSODataSourceConfig {

 // 精确到  目录，以便跟其他数据源隔离
 static final String PACKAGE = "com.ld.sso.core.mapper";
 static final String MAPPER_LOCATION = "classpath:sso_mapper/*.xml";

 @Value("${sso.datasource.url}")
 private String url;

 @Value("${sso.datasource.username}")
 private String user;

 @Value("${sso.datasource.password}")
 private String password;

 @Value("${sso.datasource.driverClassName}")
 private String driverClass;

 @Bean(name = "ssoDataSource")
// @Primary
 public DataSource ssoDataSource() {
     DruidDataSource dataSource = new DruidDataSource();
     dataSource.setDriverClassName(driverClass);
     dataSource.setUrl(url);
     dataSource.setUsername(user);
     dataSource.setPassword(password);
     return dataSource;
 }

 @Bean(name = "ssoTransactionManager")
 //@Primary
 public DataSourceTransactionManager ssoTransactionManager() {
     return new DataSourceTransactionManager(ssoDataSource());
 }

 @Bean(name = "ssoSqlSessionFactory")
 //@Primary
 public SqlSessionFactory ssoSqlSessionFactory(@Qualifier("ssoDataSource") DataSource ssoDataSource)
         throws Exception {
     final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
     sessionFactory.setDataSource(ssoDataSource);
     sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
             .getResources(SSODataSourceConfig.MAPPER_LOCATION));
     return sessionFactory.getObject();
 }
}
