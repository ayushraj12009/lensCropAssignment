package com.LensCorp.LensCorp;


import com.LensCorp.LensCorp.Controller.AuthController;
import com.LensCorp.LensCorp.DTO.LogginRequest;
import com.LensCorp.LensCorp.Model.User;
import com.LensCorp.LensCorp.Repository.UserRepository;
import com.LensCorp.LensCorp.Response.AuthResponse;
import com.LensCorp.LensCorp.Services.CustomerUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomerUserDetailsService customerUserDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser_Success() {
        // Creating a new user object
        User newUser = new User();
        newUser.setUserName("AyushRaj12009");
        newUser.setFristName("Ayush");
        newUser.setLastName("Raj");
        newUser.setEmail("ayushraj12009@gmail.com");
        newUser.setPassword("AyushRaj");

        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(null);
        // Mock save operation to return the same new user object
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        // Mock password encoding operation
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("encodedPassword");

        // Calling the createUser method of authController
        AuthResponse response = authController.createUser(newUser);
        // Checking if the response message is "Signup Success"
        assertEquals("Signup Success", response.getMessage());
    }

    @Test
    public void testCreateUser_EmailExists() {
        // user object with existing email
        User existingUser = new User();
        existingUser.setUserName("AyushRaj12009");
        existingUser.setFristName("Ayush");
        existingUser.setLastName("Raj");
        existingUser.setEmail("ayushraj12009@gmail.com");
        existingUser.setPassword("AyushRaj");

        // Assuming a user with the same email already exists in the database
        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(existingUser);

        AuthResponse response = authController.createUser(existingUser);

        // Checking if the response message is "Email already used with another account"
        assertEquals("Email already used with another account", response.getMessage());
    }

    @Test
    public void testSignIn_Success() {
        // User object with existing email
        String email = "ayushraj12009@gmail.com";
        String password = "AyushRaj12009";

        // login request with user credentials
        LogginRequest logginRequest = new LogginRequest(email, password);

        AuthResponse response = authController.signin(logginRequest);

        // Checking if the response message is "Login Success"
        assertEquals("Login Success", response.getMessage());
    }



    @Test
    public void testSignIn_InvalidCredentials() {
        // User credentials with non-existing email and incorrect password
        String email = "ayushraj12009@example.com";
        String password = "RajAyushRaj2023";

        // Assuming no user found with the provided email
        when(customerUserDetailsService.loadUserByUsername(email)).thenReturn(null);

        // login request with user credentials
        LogginRequest logginRequest = new LogginRequest(email, password);
        AuthResponse response = authController.signin(logginRequest);

        // Checking if the response message is "Invalid UserName"
        assertEquals("Invalid UserName", response.getMessage());
    }
}