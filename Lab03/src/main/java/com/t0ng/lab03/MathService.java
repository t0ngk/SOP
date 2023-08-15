package com.t0ng.lab03;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathService {
    @RequestMapping("/add/{num1}/{num2}")
    public Double add(
            @PathVariable("num1") double num1,
            @PathVariable("num2") double num2
    ) {
        return num1 + num2;
    }

    @RequestMapping("/minus/{num1}/{num2}")
    public Double minus(
            @PathVariable("num1") double num1,
            @PathVariable("num2") double num2
    ) {
        return num1 - num2;
    }

    @RequestMapping("/multiply")
    public Double multiply(
            @RequestParam("num1") double num1,
            @RequestParam("num2") double num2
    ) {
        return num1 * num2;
    }

    @RequestMapping("/divide")
    public Double divide(
            @RequestParam("num1") double num1,
            @RequestParam("num2") double num2
    ){
        return num1 / num2;
    }
}
