package com.example.lab3;

import org.springframework.web.bind.annotation.*;

@RestController
public class MathService {

    @RequestMapping(value = "/add/{num1}/{num2}", method = RequestMethod.GET)
    public double add(@PathVariable("num1") double num1, @PathVariable("num2") double num2) {
        return num1 + num2;
    }

    @RequestMapping(value = "/minus/{num1}/{num2}", method = RequestMethod.GET)
    public double minus(@PathVariable("num1") double num1, @PathVariable("num2") double num2) {
        return num1 - num2;
    }

    @RequestMapping(value = "/multiply", method = RequestMethod.GET)
    public double multiply(@RequestParam("num1") double num1, @RequestParam("num2") double num2) {
        return num1 * num2;
    }

    @RequestMapping(value = "/divide", method = RequestMethod.GET)
    public double divide(@RequestParam("num1") double num1, @RequestParam("num2") double num2) {
        return num1 / num2;
    }
}
