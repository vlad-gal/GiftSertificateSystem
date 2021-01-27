package com.epam.esm.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan("com.epam.esm")
@ConfigurationProperties("spring.datasource")
public class PersistenceConfig {
    @Setter
    private String driverClassName;
    @Setter
    private String url;
    @Setter
    private String username;
    @Setter
    private String password;
    @Setter
    private String showSql;
    @Setter
    private String dialect;
    @Setter
    private String formatSql;

    @Profile("dev")
    @Bean
    public DataSource developmentDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Profile("prod")
    @Bean
    public DataSource productionDataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(driverClassName);
            dataSource.setJdbcUrl(url);
            dataSource.setUser(username);
            dataSource.setPassword(password);
            return dataSource;
        } catch (PropertyVetoException e) {
            throw new CannotGetJdbcConnectionException("Error while get connection to database");
        }
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean managerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.epam.esm.entity");
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", dialect);
        hibernateProperties.put("hibernate.show_sql", showSql);
        hibernateProperties.put("hibernate.format_sql", formatSql);
        entityManager.setJpaProperties(hibernateProperties);
        return entityManager;
    }
}