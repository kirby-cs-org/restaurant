package ku.cs.restaurant.dto;

import ku.cs.restaurant.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerToDto {
    public CustomerDto convert(Customer customer) {
        CustomerDto customerDTO = new CustomerDto();

        customerDTO.setC_name(customer.getC_name());
        customerDTO.setC_password(customer.getC_password());
        customerDTO.setC_phone(customer.getC_phone());

        return customerDTO;
    }
}
