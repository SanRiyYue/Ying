package org.example;


import java.io.File;
import java.net.URL;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class SendEmail {
    public static void main(String[] args) {
        //服务器地址
        String smtp = "smtp.qq.com";
        //登录用户名
        String username = "3138665771@qq.com";
        //登录口令
        String password = "eatxhbvetflgdceg";
        //连接到smtp服务器
        Properties props = new Properties();
        props.put("mail.smtp.host", smtp); //SMTP主机名
        props.put("mail.smtp.port", "587");//主机端口名
        props.put("mail.smtp.auth", "true");//是否需要用户认证
        props.put("mail.smtp.starttls.enable", "true"); //启用TLS加密
        //获取Session实例
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        //设置debug模式以便调试
        session.setDebug(true);

        try {


            String body = "<h1>Hello</h1><p>Hi,this is a mail, wish you have a good time!</p><img src=\"cid:img01\"></p>";
            //创建邮件对象
            Message message = new MimeMessage(session);
            //设置发件人
            message.setFrom(new InternetAddress(username));
            //设置收件人
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("1596434623@qq.com"));
            //设置邮件主题
            message.setSubject("I'm a subject");


            Multipart multipart = new MimeMultipart();
            //添加text
            BodyPart textpart = new MimeBodyPart();


            textpart.setContent(body, "text/html; charset=utf-8");
            multipart.addBodyPart(textpart);


            //添加Imag
            BodyPart imagepart = new MimeBodyPart();
            String filename = "javamail.jpg";

            ClassLoader classloader = SendEmail.class.getClassLoader();
            URL resource = classloader.getResource(filename);

            if (resource == null) {
                throw new IllegalArgumentException("File not found: " + filename);
            }

            File file =  new File(resource.getFile());
            DataSource source = new FileDataSource(file);
            imagepart.setDataHandler(new DataHandler(source));
            imagepart.setFileName(filename);
            imagepart.setHeader("Content-ID", "<img01>");
            multipart.addBodyPart(imagepart);


            message.setContent(multipart);
            //发送邮件
            Transport.send(message);

            System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
