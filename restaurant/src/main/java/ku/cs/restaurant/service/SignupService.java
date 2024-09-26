package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.Customer;
import ku.cs.restaurant.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService {
    @Autowired
    private CustomerRepository repository;

    public void createCustomer(Customer customer) {
        repository.save(customer);
    }
}
