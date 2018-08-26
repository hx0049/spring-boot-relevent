package com.controller;

import com.service.JavaMailReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailReceiveController {
    @Autowired
    JavaMailReceiver javaMailReceiver;
    @RequestMapping("mailInfo")
    @ResponseBody
    public String mailCount(){
        return javaMailReceiver.getMailInfo();
    }
}
