package com.example.sudabangweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/main")
    public String home(Model model){
        model.addAttribute("username","맹순영"); //attributeValue에 DB에서 받아온 값 넣어줘야됨
        return "mainPage";
    }

    @GetMapping("/woi")
    public String woi(Model model){
        model.addAttribute("woi","wiwwiw");
        return "woi";
    }
}
