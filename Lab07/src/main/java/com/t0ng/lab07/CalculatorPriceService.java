package com.t0ng.lab07;

import org.springframework.stereotype.Service;

@Service
public class CalculatorPriceService {
    public double getPrice(double cost, double profit) {
        return cost + profit;
    }
}