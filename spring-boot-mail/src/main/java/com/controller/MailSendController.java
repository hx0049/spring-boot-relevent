package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.File;

import static org.junit.Assert.assertFalse;

@RestController
public class MailSendController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    TemplateEngine templateEngine;

    @RequestMapping("send")
    public String test(){
        System.out.println("---start---");
        String deliver = "huangxiang49@126.com";
        String[] receiver = {"1038371123@qq.com"};
        String[] carbonCopy = {"1609363012@qq.com"};
        String subject = "This is a simple email";
        String content = "This is a simple content";
        try {
            /*deliver = "admin2@test.com";
            receiver=new String[]{"1038371123@11.com"};
            carbonCopy =new String[]{"1609363012@qq.com"};*/
            sendSimpleEmail(deliver, receiver, null, subject, content);
            //subject = "This is a HTML content email";
            //content = "<h1>This is HTML content email </h1>";
            //sendHtmlEmail(deliver, receiver, carbonCopy, subject, content, true);
           /* subject = "This is an attachment file email from spring boot application with James mail server";
            content = "<h2>This is an attachment file email from spring boot application with James mail server</h2>";
            sendAttachmentFileEmail(deliver, receiver, carbonCopy, subject, content,
                    true, "JavaEE开发的颠覆者 Spring Boot实战  完整版.pdf", new File("C:\\Users\\hx\\Desktop\\JavaEE开发的颠覆者 Spring Boot实战  完整版.pdf"));
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "succes";
    }

    public void sendSimpleEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String content) throws Exception {

        long startTimestamp = System.currentTimeMillis();
        logger.info("Start send mail ... ");

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(deliver);
            message.setTo(receiver);
            message.setCc(carbonCopy);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            logger.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MailException e) {
            logger.error("Send mail failed, error message is : {} \n", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    public void sendHtmlEmail(String deliver, String[] receiver, String[] carbonCopy,
                              String subject, String content, boolean isHtml) throws Exception {
        long startTimestamp = System.currentTimeMillis();
        logger.info("Start send email ...");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);

            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver);
            messageHelper.setCc(carbonCopy);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, isHtml);

            mailSender.send(message);
            logger.info("Send email success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MessagingException e) {
            logger.error("Send email failed, error message is {} \n", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public void sendAttachmentFileEmail(String deliver, String[] receiver, String[] carbonCopy,
                                        String subject, String content, boolean isHtml,
                                        String fileName, File file) throws Exception {
        long startTimestamp = System.currentTimeMillis();
        logger.info("Start send mail ...");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver);
            messageHelper.setCc(carbonCopy);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, isHtml);
            messageHelper.addAttachment(fileName, file);
            messageHelper.addAttachment("spring-boot-reference-2.0.3.pdf",
                    new File("C:\\Users\\hx\\Desktop\\to28\\Spring-Boot-related\\spring-boot-reference-2.0.3.pdf"));

            mailSender.send(message);
            logger.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MessagingException e) {
            logger.error("Send mail failed, error message is {}\n", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
