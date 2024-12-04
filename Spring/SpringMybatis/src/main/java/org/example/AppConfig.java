package org.example;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.entity.User;
import org.example.service.UserService;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

@Configuration
@ComponentScan
@PropertySource("jdbc.properties")
@MapperScan("org.example.mapper")  //使用MyBatis提供的MapperFactoryBean来自动创建所有Mapper的实现类。
@EnableTransactionManagement
public class AppConfig {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);
        if (userService.fetchUserByEmail("bob@example.com") == null) {
            User bob = userService.register("bob@example.com", "password1", "Bob");
        }
        if (userService.fetchUserByEmail("alice@example.com") == null) {
            User bob = userService.register("alice@example.com", "password2", "Alice");
        }
        if (userService.fetchUserByEmail("tom@example.com") == null) {
            User bob = userService.register("tom@example.com", "password3", "Tom");
        }

        //查询所有用户

        List<User> users = userService.getAllUsers(1);
        for (User user : users) {
            System.out.println(user);
        }
        User tom = userService.login("tom@example.com", "password3");
        System.out.println(tom);
        ((ConfigurableApplicationContext) context).close();
    }


    @Value("${jdbc.url}")
    String url;

    @Value("${jdbc.username}")
    String username;

    @Value("${jdbc.password}")
    String password;


    //使用HikariConfig连接池进行连接
    @Bean
    DataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        Properties props = new Properties();
        props.setProperty("autoCommit", "false");
        props.setProperty("connectionTimeout", "5");
        props.setProperty("idleTimeout", "30");
        config.setDataSourceProperties(props);
        return new HikariDataSource(config);
    }

    //创建Mybatis Bean
    @Bean
    SqlSessionFactoryBean createSqlSessionFactoryBean(@Autowired DataSource dataSource) {
        var sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

    //创建声明事务，直接使用Spring管理的声明式事务
    @Bean
    PlatformTransactionManager createTransactionManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
