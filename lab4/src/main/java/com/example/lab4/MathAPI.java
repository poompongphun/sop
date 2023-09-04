package com.example.lab4;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MathAPI {

    @RequestMapping(value = "/plus/{num1}/{num2}", method = RequestMethod.GET)
    public double myPlus(@PathVariable("num1") double num1, @PathVariable("num2") double num2) {
        return num1 + num2;
    }

    @RequestMapping(value = "/minus/{num1}/{num2}", method = RequestMethod.GET)
    public double myMinus(@PathVariable("num1") double num1, @PathVariable("num2") double num2) {
        return num1 - num2;
    }
    @RequestMapping(value = "/divide/{num1}/{num2}", method = RequestMethod.GET)
    public double myDivide(@PathVariable("num1") double num1, @PathVariable("num2") double num2) {
        return num1 / num2;
    }

    @RequestMapping(value = "/multi/{num1}/{num2}", method = RequestMethod.GET)
    public double myMulti(@PathVariable("num1") double num1, @PathVariable("num2") double num2) {
        return num1 * num2;
    }

    @RequestMapping(value = "/mod/{num1}/{num2}", method = RequestMethod.GET)
    public double myMod(@PathVariable("num1") double num1, @PathVariable("num2") double num2) {
        return num1 % num2;
    }

    @RequestMapping(value = "/max", method = RequestMethod.POST)
    public double myMax(@RequestBody MultiValueMap<String, String> n) {
        Map<String, String> d = n.toSingleValueMap();
        double n1 = Double.parseDouble(d.get("n1"));
        double n2 = Double.parseDouble(d.get("n2"));
        return n1 > n2 ? n1 : n2;
    }
}


