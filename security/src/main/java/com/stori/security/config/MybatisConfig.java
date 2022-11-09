package com.stori.security.config;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * 1.注入配置文件 2.加载jdbc文件配置 3.开启事务 4.扫描java mapper文件地址
 */
@Configuration
public class MybatisConfig implements EnvironmentAware {

    private org.springframework.core.env.Environment environment;

    @Override
    public void setEnvironment(org.springframework.core.env.Environment environment) {
        this.environment = environment;
    }

    /**
     * 设置数据源和相应的连接属性
     * 
     * @return
     */
    @Bean("securityDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("spring.datasource.security.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.security.username"));
        // dataSource.setPassword(SecretsManager.decrypt(environment.getProperty("spring.datasource.bill.password")));
        dataSource.setPassword(environment.getProperty("spring.datasource.security.password"));
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.security.driver"));
        return dataSource;
    }

    @Bean("securitySqlSessionFactory")
    public SqlSessionFactoryBean mysqlSqlSessionFactory(@Autowired @Qualifier("securityDataSource") DataSource dataSource)
        throws Exception {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        configuration.setEnvironment(new Environment("securityEnv", new JdbcTransactionFactory(), dataSource));
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setDataSource(dataSource);

        // 手动扫描映射文件
        sqlSessionFactory
            .setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/*.xml"));
        return sqlSessionFactory;
    }

    @Bean("securityMapperScanner")
    public MapperScannerConfigurer mysqlMapperScanner(
        @Autowired @Qualifier("securitySqlSessionFactory") SqlSessionFactoryBean sqlSessionFactory) throws Exception {
        MapperScannerConfigurer scanner = new MapperScannerConfigurer();
        scanner.setSqlSessionFactory(sqlSessionFactory.getObject());
        scanner.setBasePackage("com.stori.security.dal.mapper");
        return scanner;
    }
}
