package com.example.lab3;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestRestAPI {
    @RequestMapping(value = "/helloWorld", method = RequestMethod.GET)
    public String helloWorld() {
        return "Hello World";
    }
}