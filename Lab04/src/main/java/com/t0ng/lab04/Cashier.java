package com.t0ng.lab04;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Cashier {

    @RequestMapping("/getChange/{n1}")
    public Change getChange(@PathVariable Integer n1){
        Integer money = n1;
        Change change = new Change();
        change.setB1000(money / 1000);
        money %= 1000;
        change.setB500(money / 500);
        money %= 500;
        change.setB100(money / 100);
        money %= 100;
        change.setB20(money / 20);
        money %= 20;
        change.setB10(money / 10);
        money %= 10;
        change.setB5(money / 5);
        money %= 5;
        change.setB1(money);
        return change;
    }

}
