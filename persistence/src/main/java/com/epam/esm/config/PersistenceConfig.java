package com.epam.esm.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
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

//    @Bean
//    public EntityManagerFactory entityManagerFactory(){
//
//    }


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