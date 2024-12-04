package org.example;

import com.sun.mail.pop3.POP3SSLStore;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.internet.MimeMessage.RecipientType;

public class ReceiveMail {

    final String popHost;
    final String username;
    final String password;
    final boolean debug;

    ReceiveMail(String popHost, String username, String password) {
        this.popHost = popHost;
        this.username = username;
        this.password = password;
        this.debug = true;
    }

    public static void main(String[] args) throws MessagingException, IOException {
        ReceiveMail pop = new ReceiveMail("pop.qq.com","3138665771@qq.com", "wmvmotnxdespdhdc");
        Folder folder = null;
        Store store = null;

        try {
            store = pop.createSSLStore();
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            System.out.println("Total message: " + folder.getMessageCount());
            System.out.println("New message: " + folder.getNewMessageCount());
            System.out.println("Unread message: " + folder.getUnreadMessageCount());
            System.out.println("Deleted message: " + folder.getDeletedMessageCount());
            Message[] messages = folder.getMessages();
            for (Message message : messages) {
                printMessage((MimeMessage) message);
            }
        } finally {
            if (folder != null) {
                try {
                    folder.close(true);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
            if (store != null) {
                try {
                    store.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public Store createSSLStore() throws MessagingException {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "pop3");
        props.setProperty("mail.pop3.port", "995"); //主机端口号
        props.setProperty("mail.pop3.host", this.popHost); //POP3主机名
        //启动SSL
        props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.pop3.socketFactory.port", "995");
        URLName url = new URLName("pop3", this.popHost, 995, "", this.username, this.password);
        Session session = Session.getInstance(props, null);
        session.setDebug(debug);
        Store store = new POP3SSLStore(session, url);
        store.connect();
        return store;
    }

    public static void printMessage(MimeMessage msg) throws IOException, MessagingException {
        System.out.println("--------------------------");
        System.out.println("Subject: " + MimeUtility.decodeText(msg.getSubject()));
        System.out.println("From: " + getFrom(msg));
        System.out.println("To: " + getTo(msg));
        System.out.println("Sent: " + msg.getSentDate().toString());
        System.out.println("Seen: " + msg.getFlags().contains(Flags.Flag.SEEN));
        System.out.println("Priority: " + getPriority(msg));
        System.out.println("Size: " + msg.getSize() / 1024 + "kb");
        System.out.println("Body: " + getBody(msg));
        System.out.println("--------------------------");
        System.out.println();
    }

    private static String getBody(Part part) throws MessagingException, IOException {
        if (part.isMimeType("text/*")) {
            return part.getContent().toString();
        }
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart bp = mp.getBodyPart(i);
                String body = getBody(bp);
                if (body != null) {
                    return body;
                }
            }
        }
        return "";
    }


    public static String getFrom(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
        Address[] froms = msg.getFrom();
        return addressToString(froms[0]);
    }

    public static String getTo(MimeMessage msg) throws MessagingException {
        Address[] tos = msg.getRecipients(RecipientType.TO);
        List<String> list = new ArrayList<String>();
        for (Address to : tos){
            list.add(to.toString());
        }
        return String.join(",", list);
    }

    private static String getPriority(MimeMessage msg) throws MessagingException {
        String priority = "Normal";
        String[] headers = msg.getHeader("X-Priority");
        if (headers != null) {
            String header = headers[0];
            if ("1".equals(header) || "hidh".equalsIgnoreCase(header)) {
                priority = "High";
            } else if ("5".equals(header) || "low".equalsIgnoreCase(header)) {
                priority = "Low";
            }
        }
        return priority;
    }
    public static String addressToString(Address addr) throws UnsupportedEncodingException {
        InternetAddress address = (InternetAddress) addr;
        String personal = address.getPersonal();
        return personal == null ? address.getAddress() : (MimeUtility.decodeText(personal)) ;
    }
}
