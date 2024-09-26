package ku.cs.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue
    private UUID c_id;

    private String c_name;
    private String c_password;
    private String c_phone;
}
