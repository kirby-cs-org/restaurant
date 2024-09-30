package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.user.SignupRequest;
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
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        try {
            String username = signupRequest.getUsername();
            String phone = signupRequest.getPhone();
            String password = signupRequest.getPassword();
            String confirmPassword = signupRequest.getConfirmPassword();

            if (username == null || username.isEmpty())
                return new ResponseEntity<>("Username cannot be empty", HttpStatus.BAD_REQUEST);
            if (phone == null || phone.isEmpty())
                return new ResponseEntity<>("Phone number cannot be empty", HttpStatus.BAD_REQUEST);
            if (password == null || password.isEmpty())
                return new ResponseEntity<>("Password cannot be empty", HttpStatus.BAD_REQUEST);
            if (!password.equals(confirmPassword))
                return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);

            User user = new User();
            user.setName(username);
            user.setPhone(phone);
            user.setPassword(password);

            User createdUser = userService.createUser(user);
            return new ResponseEntity<>("User created successfully: " + createdUser.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllCustomers() {
        List<User> customers = new ArrayList<>(userService.getAllCustomers());

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
