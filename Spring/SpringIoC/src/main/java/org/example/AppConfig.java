package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.example.service.User;
import org.example.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@EnableTransactionManagement
@PropertySource("jdbc.properties")
public class AppConfig {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);
        // 插入Bob:
        if (userService.fetchUserByEmail("bob@example.com") == null) {
            userService.register("bob@example.com", "password", "Bob");
        }
        // 插入Alice:
        if (userService.fetchUserByEmail("alice@example.com") == null) {
            userService.register("alice@example.com", "password2", "Alice");
        }
        // 插入Tom:
        if (userService.fetchUserByEmail("tom@example.com") == null) {
            userService.register("tom@example.com", "password2", "Tom");
        }
        //插入Root:
        try {
            userService.register("Root@example.com", "password3", "Root");
        } catch ( RuntimeException e ) {
            System.err.println(e.getMessage());
        }

        //查询所有用户:
        for (User u : userService.getUsers(1)) {
            System.out.println(u);
        }

        ((ConfigurableApplicationContext) context).close();
    }


    @Value("${jdbc.url}")
    String jdbcUrl;

    @Value("${jdbc.username}")
    String jdbcUsername;

    @Value("${jdbc.password}")
    String jdbcPassword;

    //HikariCP连接池
    @Bean
    DataSource createDataSource() {
        System.out.println("Creating DataSource Object");
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(jdbcUsername);
        config.setPassword(jdbcPassword);
        config.addDataSourceProperty("autoCommit", "true");
        config.addDataSourceProperty("connectionTimeout", "5");
        config.addDataSourceProperty("idleTimeout", "60");
        return new HikariDataSource(config);
    }

    //JdbcTemplate ORM框架
    @Bean
    JdbcTemplate createJdbcTemplate(@Autowired DataSource dataSource) {
        System.out.println("Creating JDBC Template Object");
        return new JdbcTemplate(dataSource);
    }

    //事务处理
    @Bean
    PlatformTransactionManager createTransactionManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
