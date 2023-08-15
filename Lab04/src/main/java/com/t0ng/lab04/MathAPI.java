package com.t0ng.lab04;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MathAPI {

    @RequestMapping("/add/{n1}/{n2}")
    public Double myAdd(@PathVariable Double n1, @PathVariable Double n2) {
        return n1 + n2;
    }

    @RequestMapping("/minus/{n1}/{n2}")
    public Double myMinus(@PathVariable Double n1, @PathVariable Double n2) {
        return n1 - n2;
    }

    @RequestMapping("/divide/{n1}/{n2}")
    public Double myDivide(@PathVariable Double n1, @PathVariable Double n2) {
        return n1 / n2;
    }

    @RequestMapping("/multi/{n1}/{n2}")
    public Double myMulti(@PathVariable Double n1, @PathVariable Double n2) {
        return n1 * n2;
    }

    @RequestMapping("/mod/{n1}/{n2}")
    public Double myMod(@PathVariable Double n1, @PathVariable Double n2) {
        return n1 % n2;
    }

    @RequestMapping(value = "/max", method = RequestMethod.POST)
    public Double myMax(@RequestBody MultiValueMap<String, String> formData) {
        Map<String, String> map = formData.toSingleValueMap();
        return Math.max(Double.parseDouble(map.get("n1")), Double.parseDouble(map.get("n2")));
    }

}
