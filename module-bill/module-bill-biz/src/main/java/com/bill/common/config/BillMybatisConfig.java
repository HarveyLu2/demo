package com.bill.common.config;

import com.stori.sofa.security.secrets.SecretsManager;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
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
 * 1.注入配置文件
 * 2.加载jdbc文件配置
 * 3.开启事务
 * 4.扫描java mapper文件地址
 */
@Configuration
public class BillMybatisConfig implements EnvironmentAware {

    private org.springframework.core.env.Environment environment;

    @Override
    public void setEnvironment(org.springframework.core.env.Environment environment) {
        this.environment = environment;
    }

    /**
     * 设置数据源和相应的连接属性
     * @return
     */
    @Bean("billDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("spring.datasource.bill.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.bill.username"));
//        dataSource.setPassword(SecretsManager.decrypt(environment.getProperty("spring.datasource.bill.password")));
        dataSource.setPassword(environment.getProperty("spring.datasource.bill.password"));
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.bill.driver"));
        return dataSource;
    }

    @Bean("billSqlSessionFactory")
    public SqlSessionFactoryBean mysqlSqlSessionFactory(@Autowired @Qualifier("billDataSource") DataSource dataSource) throws Exception {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        configuration.setEnvironment(new Environment("billEnv", new JdbcTransactionFactory(), dataSource));
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setDataSource(dataSource);

        // 手动扫描映射文件
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/*.xml"));
        return sqlSessionFactory;
    }

    @Bean("billMapperScanner")
    public MapperScannerConfigurer mysqlMapperScanner(@Autowired @Qualifier("billSqlSessionFactory") SqlSessionFactoryBean sqlSessionFactory) throws Exception {
        MapperScannerConfigurer scanner = new MapperScannerConfigurer();
        scanner.setSqlSessionFactory(sqlSessionFactory.getObject());
        scanner.setBasePackage("com.bill.common.dal.mapper");
        return scanner;
    }
}
