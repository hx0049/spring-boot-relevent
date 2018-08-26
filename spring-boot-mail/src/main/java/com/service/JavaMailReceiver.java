package com.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Properties;

@Service
public class JavaMailReceiver {
    @Value("${mail.pop3.host}")
    private String pop3Server;//smtp.qq.com
    @Value("${mail.pop3.port}")
    private String pop3Port;//995
    @Value("${mail.pop3.auth}")
    private String pop3Auth;//true

    @Value("${mail.pop3.socketFactory.class}")
    private String sslFactory;//javax.net.ssl.SSLSocketFactory
    @Value("${mail.pop3.socketFactory.fallback}")
    private String sslFallback;//false
    @Value("${mail.pop3.socketFactory.port}")//995
    private String sslPort;

    @Value("${mail.pop.host}")
    private String popHost;//pop.qq.com
    @Value("${mail.pop.port}")
    private Integer popPort;//995
    @Value("${mail.pop.user}")
    private String popUser;//xxx@qq.com
    @Value("${mail.pop.password}")
    private String popPassword;//密码或鉴权码


    private Session session = null;
    private Store store = null;
    private Folder inbox = null;
    private URLName url = null;


    public Session getSession() {
        if (session == null) {
            //设置SSL连接、邮件环境
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Properties props = System.getProperties();
            props.setProperty("mail.pop3.host", pop3Server);
            props.setProperty("mail.pop3.port", pop3Port);
            props.setProperty("mail.pop3.auth", pop3Auth);
            props.setProperty("mail.pop3.socketFactory.class", sslFactory);
            props.setProperty("mail.pop3.socketFactory.fallback", sslFallback);
            props.setProperty("mail.pop3.socketFactory.port", sslPort);
            session = Session.getDefaultInstance(props, null);
            url = new URLName("pop3", popHost, popPort,
                    null, popUser, popPassword);
        }
        return session;
    }

    public String getMailInfo() {
        StringBuilder sb = new StringBuilder();
        try {
            //得到邮件仓库并连接
            store = getSession().getStore(url);
            store.connect();
            //得到收件箱并抓取邮件
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            FetchProfile profile = new FetchProfile();
            profile.add(FetchProfile.Item.ENVELOPE);
            Message[] messages = inbox.getMessages();
            inbox.fetch(messages, profile);
            //打印收件箱邮件部分信息
            int length = messages.length;

            sb.append("收件箱的邮件数：" + length).append("<br/>");
            sb.append("-------------------------------------------<br/><br/>");
            for (int i = 0; i < length; i++) {
                String from = MimeUtility.decodeText(messages[i].getFrom()[0].toString());
                InternetAddress ia = new InternetAddress(from);
                sb.append("发件人：" + ia.getPersonal() + '(' + ia.getAddress() + ')').append("<br/>");
                sb.append("主题：" + messages[i].getSubject()).append("<br/>");
                sb.append("邮件大小：" + messages[i].getSize()).append("<br/>");
                sb.append("邮件发送时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(messages[i].getSentDate())).append("<br/>");
                sb.append("-------------------------------------------<br/>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inbox.close(false);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            try {
                store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
