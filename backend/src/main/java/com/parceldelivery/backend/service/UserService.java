package com.parceldelivery.backend.service;

import com.parceldelivery.backend.model.Role;
import com.parceldelivery.backend.model.User;
import com.parceldelivery.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String customerName, String email, String mobile, String address, String password, String preferences) {
        User user = new User(customerName, email, mobile, address, passwordEncoder.encode(password));
        user.setCustomerId(generateCustomerId());
        user.setPreferences(preferences);
        user.setRole(Role.CUSTOMER);
        
        return userRepository.save(user);
    }

    public User registerOfficer(String customerName, String email, String mobile, String address, String password) {
        User user = new User(customerName, email, mobile, address, passwordEncoder.encode(password));
        user.setCustomerId(generateCustomerId());
        user.setRole(Role.OFFICER);
        
        return userRepository.save(user);
    }

    private String generateCustomerId() {
        String customerId;
        do {
            customerId = "CUST" + String.format("%06d", new Random().nextInt(1000000));
        } while (userRepository.existsByCustomerId(customerId));
        return customerId;
    }

    public boolean existsByCustomerId(String customerId) {
        return userRepository.existsByCustomerId(customerId);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}