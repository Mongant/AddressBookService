package com.mongant.configuration;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@ComponentScan(basePackages = {"com.mongant.service", "com.mongant.repository"})
@PropertySource("classpath:main.properties")
public class AppConfig {

    @Value("${h2.url}")
    private String url;

    @Value("${h2.password}")
    private String password;

    @Value("${h2.username}")
    private String userName;

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
