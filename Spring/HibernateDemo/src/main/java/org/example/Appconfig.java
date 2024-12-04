package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


import jakarta.persistence.EntityManagerFactory;
import org.example.entity.AbstractEntity;
import org.example.entity.User;
import org.example.service.Uservice;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;


@Configuration
@ComponentScan
@EnableTransactionManagement
@PropertySource("jdbc.properties")
public class Appconfig {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Appconfig.class);
        Uservice uservice = context.getBean(Uservice.class);
        uservice.register("bob@example.com", "password1", "Bob");
        uservice.register("john@example.com", "password2", "John");
        uservice.register("alice@example.com", "password3", "Alice");
        List<User> users = uservice.getUsers(1);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Value("${jdbc.url}")
    String jdbcUrl;

    @Value("${jdbc.username}")
    String jdbcUsername;

    @Value("${jdbc.password}")
    String jdbcPassword;

    @Bean
    DataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(jdbcUsername);
        config.setPassword(jdbcPassword);
        config.addDataSourceProperty("autoCommit", "false");
        config.addDataSourceProperty("connectionTimeout", "5");
        config.addDataSourceProperty("idleTimeout", "60");
        return new HikariDataSource(config);
    }

    //启动Hibernate
    @Bean
    public LocalContainerEntityManagerFactoryBean createEntityManagerFactory(@Autowired DataSource dataSource) {
        var emFactory = new LocalContainerEntityManagerFactoryBean();
        //注入DataSource:
        emFactory.setDataSource(dataSource);
        //扫描指定的package获取所有的Entity. class:
        emFactory.setPackagesToScan(AbstractEntity.class.getPackageName());
        //使用Hibernate作为JPA实现:
        emFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        var props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", "update");
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        props.setProperty("hibernate.show_sql", "true");
        emFactory.setJpaProperties(props);
        return emFactory;
    }

    @Bean
    PlatformTransactionManager createTransactionManager(@Autowired EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
