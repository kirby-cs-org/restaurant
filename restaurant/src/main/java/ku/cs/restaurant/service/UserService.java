package ku.cs.restaurant.service;

import ku.cs.restaurant.dto.user.SignupRequest;
import ku.cs.restaurant.dto.user.SignupResponse;
import ku.cs.restaurant.entity.User;
import ku.cs.restaurant.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<SignupResponse> createUser(SignupRequest user) {
        SignupResponse signupResponse = new SignupResponse();
        Optional<User> existedUser = userRepository.findByUsername(user.getUsername());

        if (existedUser.isPresent()) {
            signupResponse.setMessage("Username already exists");
            return new ResponseEntity<>(signupResponse, HttpStatus.CONFLICT);
        }

        String username = user.getUsername();
        String phone = user.getPhone();
        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();
        String role = (user.getRole() != null && !user.getRole().isEmpty()) ? user.getRole() : "CUSTOMER";

        if (!password.equals(confirmPassword)) {
            signupResponse.setMessage("Passwords do not match");
            return new ResponseEntity<>(signupResponse, HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(password);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPhone(phone);
        newUser.setPassword(encodedPassword);
        newUser.setRole(role);

        userRepository.save(newUser);

        signupResponse.setMessage("User created");
        return new ResponseEntity<>(signupResponse, HttpStatus.CREATED);
    }

    public List<User> getAllCustomers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
