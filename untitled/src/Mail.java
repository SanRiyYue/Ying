
import com.sun.net.httpserver.Authenticator;

import java.util.Properties;

public class Mail {
    public static void main(String[] args) {
        //服务器地址
        String smtp = "smtp.qq.com";
        //登录用户名
        String username = "3138665771@qq.com";
        //登录口令
        String password = "123456";
        //连接到SMTP服务器587端口:
        Properties props = new Properties();
        props.put("mail.smtp.host", smtp); //SMTP主机名
        props.put("mail.smtp.port", "587"); //主机端口名
        props.put("mail.smtp.auth", "true");//是否需要用户认证
        props.put("mail.smtp.starttls.enable", "true"); //启用TLS加密

        //获取Session实例:
    }
}
