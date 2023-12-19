package com.example.web_shop_ptit.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test/")
public class testController {
    @RequestMapping("")
    private String testPage(){
        return "test";
    }
}
