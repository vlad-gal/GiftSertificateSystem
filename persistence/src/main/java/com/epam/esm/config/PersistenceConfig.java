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
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan("com.epam.esm")
@ConfigurationProperties("spring.datasource")
public class PersistenceConfig {
    private static final String PACKAGE_TO_SCAN = "com.epam.esm.entity";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
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

    @Profile("test")
    @Bean
    public DataSource testDataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql").addScript("classpath:test-data.sql").setScriptEncoding("UTF-8").build();
    }

    @Profile(value = {"dev", "prod"})
    @Bean
    public LocalContainerEntityManagerFactoryBean managerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan(PACKAGE_TO_SCAN);
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties hibernateProperties = new Properties();
        hibernateProperties.put(HIBERNATE_DIALECT, dialect);
        hibernateProperties.put(HIBERNATE_SHOW_SQL, showSql);
        hibernateProperties.put(HIBERNATE_FORMAT_SQL, formatSql);
        entityManager.setJpaProperties(hibernateProperties);
        return entityManager;
    }

    @Profile("test")
    @Bean
    public LocalContainerEntityManagerFactoryBean testManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan(PACKAGE_TO_SCAN);
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return entityManager;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}