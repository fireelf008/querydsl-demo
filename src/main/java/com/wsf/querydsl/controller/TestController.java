package com.wsf.querydsl.controller;

import com.wsf.querydsl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    @ResponseBody
    public String test(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        this.userService.test(pageRequest);
        return "hello world!";
    }
}
