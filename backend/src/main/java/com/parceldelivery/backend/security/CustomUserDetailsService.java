package com.parceldelivery.backend.security;

import com.parceldelivery.backend.model.User;
import com.parceldelivery.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String customerId) throws UsernameNotFoundException {
        User user = userRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with customer ID: " + customerId));

        return UserPrincipal.create(user);
    }
}