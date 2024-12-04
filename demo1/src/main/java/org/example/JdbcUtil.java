package org.example;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

//获取到db.properties文件中的数据库信息
public class JdbcUtil {
    //私有变量
    private static String driver;
    private static String url;
    private static String user;
    private static String password;


    //静态块
    static {
        try {
            //1.新建属性集对象
            Properties props = new Properties();
            //2.通过反射，新建字符输入流，读取db.properties文件
            InputStream input = JdbcUtil.class.getClassLoader().getResourceAsStream("db.properties");
            //3.将输入流中读取到的属性，加载到properties属性对象集
            props.load(input);
            //4.根据键，获取properties中对应的值
            driver = props.getProperty("driver");
            url = props.getProperty("url");
            user = props.getProperty("user");
            password = props.getProperty("password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //返回数据库连接
    public static Connection getConnection() {
        try{
            //注册数据库的驱动
            Class.forName(driver);
            //获取数据库连接（内容依次为：主机名、端口、用户名和密码）
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
