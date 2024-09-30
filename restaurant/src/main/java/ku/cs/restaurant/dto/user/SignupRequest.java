package ku.cs.restaurant.dto.user;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String phone;
}
