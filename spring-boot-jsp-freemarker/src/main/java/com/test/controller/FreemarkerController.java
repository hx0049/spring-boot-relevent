package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FreemarkerController {
    @RequestMapping("/freemarker")
    public String freemarker(){
        return "templates";
    }
}
