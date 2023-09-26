package com.t0ng.lab07;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorPriceController {

    @Autowired
    private CalculatorPriceService calculatorPriceService;

    @RequestMapping(value = "/getPrice/{cost}/{price}", method = RequestMethod.GET)
    public double serviceGetProducts(@PathVariable double cost, @PathVariable double price){
        return calculatorPriceService.getPrice(cost, price);
    }
}