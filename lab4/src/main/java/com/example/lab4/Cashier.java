package com.example.lab4;

import org.springframework.web.bind.annotation.*;

@RestController
public class Cashier {
    @RequestMapping(value = "/getChange/{money}", method = RequestMethod.GET)
    public Change getChange(@PathVariable("money") int money) {
        Change bank = new Change();
        while (money >= 1000) {
            money -= 1000;
            bank.setB1000(bank.getB1000() + 1);
        }
        while (money >= 500) {
            money -= 500;
            bank.setB500(bank.getB500() + 1);
        }
        while (money >= 100) {
            money -= 100;
            bank.setB100(bank.getB100() + 1);
        }
        while (money >= 20) {
            money -= 20;
            bank.setB20(bank.getB20() + 1);
        }
        while (money >= 10) {
            money -= 10;
            bank.setB10(bank.getB10() + 1);
        }
        while (money >= 5) {
            money -= 5;
            bank.setB5(bank.getB5() + 1);
        }
        while (money >= 1) {
            money -= 1;
            bank.setB1(bank.getB1() + 1);
        }
        return bank;
    }
}
