package ku.cs.restaurant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {
    @GetMapping("/menu")
    public String home() {
        return "Hello, GET";
    }
}
