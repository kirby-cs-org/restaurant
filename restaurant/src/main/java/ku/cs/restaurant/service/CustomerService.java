package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.Customer;
import ku.cs.restaurant.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import jakarta.validation.Valid;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    // สมัครสมาชิก (insert) C
    public void createCustomer(@Valid Customer customer) {
        repository.save(customer);
    }

    // ดูรายชื่อสมาชิก (select) R
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }
}
