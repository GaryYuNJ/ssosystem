package com.ld.sso.core.dbconfigure;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;



@Configuration
//扫描 Mapper 接口并容器管理
@MapperScan(basePackages = CRMDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "crmSqlSessionFactory")
public class CRMDataSourceConfig {

 // 精确到 crm 目录，以便跟其他数据源隔离
 static final String PACKAGE = "com.ld.sso.crm.mapper";
 static final String MAPPER_LOCATION = "classpath:crm_mapper/*.xml";

 @Value("${crm.datasource.url}")
 private String url;

 @Value("${crm.datasource.username}")
 private String user;

 @Value("${crm.datasource.password}")
 private String password;

 @Value("${crm.datasource.driverClassName}")
 private String driverClass;

 @Bean(name = "crmDataSource")
 @Primary
 public DataSource crmDataSource() {
     DruidDataSource dataSource = new DruidDataSource();
     dataSource.setDriverClassName(driverClass);
     dataSource.setUrl(url);
     dataSource.setUsername(user);
     dataSource.setPassword(password);
     return dataSource;
 }

 @Bean(name = "crmTransactionManager")
 @Primary
 public DataSourceTransactionManager crmTransactionManager() {
     return new DataSourceTransactionManager(crmDataSource());
 }

 @Bean(name = "crmSqlSessionFactory")
 @Primary
 public SqlSessionFactory crmSqlSessionFactory(@Qualifier("crmDataSource") DataSource crmDataSource)
         throws Exception {
     final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
     sessionFactory.setDataSource(crmDataSource);
     sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
             .getResources(CRMDataSourceConfig.MAPPER_LOCATION));
     return sessionFactory.getObject();
 }
}
