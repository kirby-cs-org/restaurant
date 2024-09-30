package ku.cs.restaurant.controller;

import ku.cs.restaurant.entity.User;
import ku.cs.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class CustomerController {
    @Autowired
    private UserService service;

    // สมัครสมาชิก
    @PostMapping("/customer/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        try {
            User createdCustomer = service.createUser(user);
            return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ดูรายชื่อสมาชิก
    @GetMapping("/customer")
    public ResponseEntity<List<User>> getAllCustomers() {
        List<User> customerDTOS = new ArrayList<>(service.getAllCustomers());

        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
    }
}
