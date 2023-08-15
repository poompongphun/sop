package com.example.lab3;

import org.springframework.web.bind.annotation.*;

@RestController
public class GeneratePasswordService {

    @RequestMapping(path = "{name:[a-zA-Z]+}.generate", method = RequestMethod.GET)
    public String generate(@PathVariable("name") String name) {
        String password = String.valueOf((int)(Math.random() * 1000000000));
        return "Hi, " + name + "\n" + "Your new password is " + password + ".";
    }
}
