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
@MapperScan(basePackages = OADataSourceConfig.PACKAGE, sqlSessionFactoryRef = "oaSqlSessionFactory")
public class OADataSourceConfig {

 // 精确到  目录，以便跟其他数据源隔离
 static final String PACKAGE = "com.ld.sso.oa.mapper";
 static final String MAPPER_LOCATION = "classpath:oa_mapper/*.xml";

 @Value("${oa.datasource.url}")
 private String url;

 @Value("${oa.datasource.username}")
 private String user;

 @Value("${oa.datasource.password}")
 private String password;

 @Value("${mysql.datasource.driverClassName}")
 private String driverClass;

 @Bean(name = "oaDataSource")
// @Primary
 public DataSource oaDataSource() {
     DruidDataSource dataSource = new DruidDataSource();
     dataSource.setDriverClassName(driverClass);
     dataSource.setUrl(url);
     dataSource.setUsername(user);
     dataSource.setPassword(password);
     return dataSource;
 }

 @Bean(name = "oaTransactionManager")
 //@Primary
 public DataSourceTransactionManager oaTransactionManager() {
     return new DataSourceTransactionManager(oaDataSource());
 }

 @Bean(name = "oaSqlSessionFactory")
 //@Primary
 public SqlSessionFactory oaSqlSessionFactory(@Qualifier("oaDataSource") DataSource oaDataSource)
         throws Exception {
     final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
     sessionFactory.setDataSource(oaDataSource);
     sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
             .getResources(OADataSourceConfig.MAPPER_LOCATION));
     return sessionFactory.getObject();
 }
}
