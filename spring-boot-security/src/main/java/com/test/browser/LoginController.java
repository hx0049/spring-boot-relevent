package com.test.browser;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "用户名或密码错误");
        }
        return "login_page";
    }
    /*@PostMapping(value = "/login")
    public String loginCompanyForm(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("name","hx");
        }  catch (Exception e) {
            //Flash session attribute must not exist. Do nothing and proceed.
        }
        return "hx";
    }*/

}
