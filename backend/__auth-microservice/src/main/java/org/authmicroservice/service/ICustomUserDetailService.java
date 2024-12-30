package org.authmicroservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ICustomUserDetailService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
