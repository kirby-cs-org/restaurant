package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.CustomerDto;
import ku.cs.restaurant.dto.CustomerToDto;
import ku.cs.restaurant.dto.DtoToCustomer;
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
    @Autowired
    private CustomerToDto customerToDTO;
    @Autowired
    private DtoToCustomer dtoToCustomer;

    // สมัครสมาชิก (insert) C
    @PostMapping("/customer/signup")
    public ResponseEntity<CustomerDto> signUp(@RequestBody CustomerDto customerDto) {
        try {
            Customer customer = dtoToCustomer.convert(customerDto);
            service.createCustomer(customer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ดูรายชื่อสมาชิก (select) R
    @GetMapping("/customer")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customerDTOS = new ArrayList<>();
        for (Customer customer : service.getAllCustomers()) {
            customerDTOS.add(customerToDTO.convert(customer));
        }

        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
    }
}
