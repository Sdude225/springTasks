package com.example.springTask.cotrollers;

import org.springframework.web.bind.annotation.GetMapping;

public class UserController {

    @GetMapping(value = "/")
    public String index() {
        return "Hello wolrd";
    }

    @GetMapping(value = "/demo")
    public String privateArea() {
        return "welcoom";
    }

}
