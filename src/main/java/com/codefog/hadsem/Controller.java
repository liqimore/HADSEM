package com.codefog.hadsem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Controller {


    @GetMapping("/")
    public String Hello(){

        return "hello world! 2019.04.11111111";
    }
}
