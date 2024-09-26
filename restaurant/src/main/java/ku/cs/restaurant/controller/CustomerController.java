package ku.cs.restaurant.controller;

import ku.cs.restaurant.entity.Customer;
import ku.cs.restaurant.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomerController {
    @Autowired
    private SignupService signupService;

    @PostMapping("/signup")
    public String signUp(@ModelAttribute Customer customer) {
        return "";
    }

}
