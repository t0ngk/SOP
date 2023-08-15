package com.t0ng.lab03;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class GeneratePasswordService {

    @RequestMapping(value = "/{name:[a-z]+}.generate", method = {RequestMethod.GET})
    public String generate(
            @PathVariable("name") String name
    ) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            password.append(String.format("%s", new Random().nextInt(10)));
        }
        return String.format("Hi,%s\nYour new password is %s", name, password.toString());
    }
}
