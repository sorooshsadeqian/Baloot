package Baloot.Servlets;

import Baloot.Domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class Test {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public User test() {
        return new User("shayan", "adr", "email", "pass", "birth", 10);
    }
}
