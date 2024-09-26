package ku.cs.restaurant.controller;

import ku.cs.restaurant.entity.Customer;
import ku.cs.restaurant.service.CustomerService;
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
    private CustomerService service;

    // สมัครสมาชิก (insert) C
    @PostMapping("/customer/signup")
    public ResponseEntity<Customer> signUp(@RequestBody Customer customer) {
        try {
            Customer createdCustomer = service.createCustomer(customer);
            return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ดูรายชื่อสมาชิก (select) R
    @GetMapping("/customer")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customerDTOS = new ArrayList<>(service.getAllCustomers());

        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
    }
}
