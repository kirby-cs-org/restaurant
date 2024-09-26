package ku.cs.restaurant.dto;

import ku.cs.restaurant.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class DtoToCustomer {
    public Customer convert(CustomerDto customerDto) {
        Customer customer = new Customer();

        customer.setC_name(customerDto.getC_name());
        customer.setC_password(customerDto.getC_password());
        customer.setC_phone(customerDto.getC_phone());

        return customer;
    }
}
