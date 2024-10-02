package ku.cs.restaurant.controller;

import jakarta.validation.Valid;
import ku.cs.restaurant.dto.user.SigninRequest;
import ku.cs.restaurant.dto.user.SigninResponse;
import ku.cs.restaurant.dto.user.SignupRequest;
import ku.cs.restaurant.dto.user.SignupResponse;
import ku.cs.restaurant.entity.User;
import ku.cs.restaurant.exception.UserRegistrationException;
import ku.cs.restaurant.service.AuthService;
import ku.cs.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            ResponseEntity<SignupResponse> createdUser = userService.createUser(signupRequest);
            return createdUser;
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<String> handleUserRegistrationException(UserRegistrationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<SigninResponse> signin(@RequestBody SigninRequest signinRequest) {
        return authService.signIn(signinRequest);
    }

}
