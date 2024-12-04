package org.example;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class DatabaseInitializer {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PlatformTransactionManager txManger;

    @PostConstruct
    public void init() {
        System.out.println("Initializing Database");
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS users ( " +
                "id BIGINT IDENTITY NOT NULL PRIMARY KEY, " +
                "email VARCHAR(100) NOT NULL, " +
                "password VARCHAR(100) NOT NULL, " +
                "name VARCHAR(100) NOT NULL, " +
                "UNIQUE (email))");
    }
}
