package com.library.lendit_book_kiosk.Security.Config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration("AppDataSource")
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"com.library.lendit_book_kiosk"})
public class AppDataSource {
    private static final Logger log = LoggerFactory.getLogger(AppDataSource.class);
    @Autowired
    private Environment env;

    /**
     * Defines the datasource connection properties for our application.
     * spring.datasource.url=jdbc:mysql://localhost:3306/Lendit_Book_Kiosk
     * spring.datasource.username=root
     * spring.datasource.password=developer
     * spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
     * @see javax.sql.DataSource;
     * @return the DataSource object containing the database properties.
     */
    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        log.info("\nDataSource: {}\n", dataSource.toString());
        return dataSource;
    }

}