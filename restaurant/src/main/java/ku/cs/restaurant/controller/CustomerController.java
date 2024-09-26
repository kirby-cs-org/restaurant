package ku.cs.restaurant.controller;

import ku.cs.restaurant.entity.Customer;
import ku.cs.restaurant.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomerController {
    @Autowired
    private SignupService service;

    @PostMapping("/signup")
    public String signUp(@RequestBody Customer customer) {
        service.createCustomer(customer);
        return "Success";
    }
}
