package com.test.browser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "test";
    }
    @RequestMapping("/welcome")
    @ResponseBody
    public String welcome(){
        return "welcome";
    }
}
