package com.epam.esm.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db-prod.properties")
@ComponentScan("com.epam.esm")
public class PersistenceConfig {

    @Profile("dev")
    @Bean
    public DataSource developmentDataSource(@Value("${spring.database.driverClassName}") String driverName,
                                            @Value("${spring.database.url}") String url,
                                            @Value("${spring.database.username}") String username,
                                            @Value("${spring.database.password}") String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Profile("prod")
    @Bean
    public DataSource productionDataSource(@Value("${spring.database.driverClassName}") String driverName,
                                           @Value("${spring.database.url}") String url,
                                           @Value("${spring.database.username}") String username,
                                           @Value("${spring.database.password}") String password) {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(driverName);
            dataSource.setJdbcUrl(url);
            dataSource.setUser(username);
            dataSource.setPassword(password);
            return dataSource;
        } catch (PropertyVetoException e) {
            throw new CannotGetJdbcConnectionException("Error while get connection to database");
        }
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource, @Value("${hibernate.show_sql}") String showSql,
                                                  @Value("${hibernate.dialect}") String dialect) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.epam.esm.entity");
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("", true);
        hibernateProperties.put("", "");

        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

}