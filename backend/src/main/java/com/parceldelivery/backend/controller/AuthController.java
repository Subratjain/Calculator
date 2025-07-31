package com.parceldelivery.backend.controller;

import com.parceldelivery.backend.dto.*;
import com.parceldelivery.backend.model.User;
import com.parceldelivery.backend.security.JwtUtils;
import com.parceldelivery.backend.security.UserPrincipal;
import com.parceldelivery.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getCustomerId(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getCustomerId(),
                userDetails.getCustomerName(),
                userDetails.getEmail(),
                userDetails.getAuthorities().iterator().next().getAuthority()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userService.existsByCustomerId(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Customer ID is already taken!"));
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = userService.registerUser(
                signUpRequest.getCustomerName(),
                signUpRequest.getEmail(),
                signUpRequest.getMobile(),
                signUpRequest.getAddress(),
                signUpRequest.getPassword(),
                signUpRequest.getPreferences()
        );

        return ResponseEntity.ok(new SignupResponse(
                "Customer Registration successful.",
                user.getCustomerId(),
                user.getCustomerName(),
                user.getEmail()
        ));
    }
}