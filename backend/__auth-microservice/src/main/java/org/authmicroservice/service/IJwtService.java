package org.authmicroservice.service;


import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Map;

public interface IJwtService {
    //String generateToken(String email);
    String createToken(Map<String, Object> claims, UserDetails userDetails);
    Key getSignKey();

    //String generateToken(String username, ERole role);

    String generateToken(String username, UserDetails userDetails);
}
