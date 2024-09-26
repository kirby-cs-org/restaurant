package ku.cs.restaurant.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CustomerDTO {
    private UUID c_id;
    private String c_name;
    private String c_phone;
    private String c_password;
}
