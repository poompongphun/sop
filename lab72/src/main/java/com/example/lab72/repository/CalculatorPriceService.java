package com.example.lab72.repository;

import org.springframework.stereotype.Service;

@Service
public class CalculatorPriceService {
    public double getPrice(double pdCost, double pdProfit) {
        return pdCost + pdProfit;
    }
}
