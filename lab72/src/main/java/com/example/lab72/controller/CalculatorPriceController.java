package com.example.lab72.controller;

import com.example.lab72.repository.CalculatorPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorPriceController {

    @Autowired
    private CalculatorPriceService calService;
    @RequestMapping(value = "/getPrice/{cost}/{profit}", method = RequestMethod.GET)
    public double serviceGetProduct(@PathVariable("cost") double cost, @PathVariable("profit") double profit) {
        return calService.getPrice(cost, profit);
    }
}
