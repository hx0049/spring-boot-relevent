package com.hx.controller;

import com.hx.primary.entity.User;
import com.hx.primary.repo.UserRepo;
import com.hx.secondary.entity.Person;
import com.hx.secondary.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PersonRepo personRepo;

    @RequestMapping("/test")
    public String test(){
        userRepo.save(new User("aa","bb"));
        personRepo.save(new Person("cc","dd"));

        System.out.println(userRepo.findAll());
        System.out.println(personRepo.findAll());
        return "success";
    }
}
