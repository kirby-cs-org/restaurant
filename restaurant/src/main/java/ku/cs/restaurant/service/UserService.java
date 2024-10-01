package ku.cs.restaurant.service;

import ku.cs.restaurant.dto.user.SignupRequest;
import ku.cs.restaurant.entity.User;
import ku.cs.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(SignupRequest user) {
        String username = user.getUsername();
        String phone = user.getPhone();
        String password = user.getPassword();
        String role = user.getRole();

        String encodedPassword = passwordEncoder.encode(password);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPhone(phone);
        newUser.setPassword(encodedPassword);
        newUser.setRole(role);

        return userRepository.save(newUser);
    }

    public List<User> getAllCustomers() {
        return userRepository.findAll();
    }
}
