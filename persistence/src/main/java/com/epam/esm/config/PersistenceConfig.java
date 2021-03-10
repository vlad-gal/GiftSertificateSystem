package com.epam.esm.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@ComponentScan("com.epam.esm")
@EnableJpaRepositories("com.epam.esm")
@ConfigurationProperties("spring.c3p0")
public class PersistenceConfig {
    private static final String PACKAGE_TO_SCAN = "com.epam.esm.entity";
    @Setter
    private String driverClass;
    @Setter
    private String jdbcUrl;
    @Setter
    private String user;
    @Setter
    private String password;

    @Bean
    @Profile("dev")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource developmentDataSource() {
        return DataSourceBuilder.create().type(DriverManagerDataSource.class).build();
    }

    @Bean
    @Profile("prod")
    public DataSource productionDataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(driverClass);
            dataSource.setJdbcUrl(jdbcUrl);
            dataSource.setUser(user);
            dataSource.setPassword(password);
            return dataSource;
        } catch (PropertyVetoException e) {
            throw new CannotGetJdbcConnectionException("Error while get connection to database");
        }
    }

    @Profile(value = {"dev", "prod"})
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        return entityManagerFactoryBuilder.dataSource(dataSource).packages(PACKAGE_TO_SCAN).build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}