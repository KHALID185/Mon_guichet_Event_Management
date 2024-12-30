package org.authmicroservice.service;

import lombok.AllArgsConstructor;
import org.authmicroservice.client.UserServiceClient;
import org.authmicroservice.enums.Active;
import org.authmicroservice.model.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor
public class CustomUserDetailsService implements ICustomUserDetailService{
    private final UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userServiceClient.getUserByEmail(email).getBody();
        assert user != null;
        if(user.getIsActive() == Active.INACTIVE) throw new RuntimeException("User is not active");
        if ( user == null ) throw new UsernameNotFoundException("Invalid Email or password.");
        if( !user.isEnabled()) throw  new RuntimeException("Please enable your account.");
        return new CustomUserDetails(user);
    }
}
