package org.authmicroservice.model;

import lombok.AllArgsConstructor;
import org.authmicroservice.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final UserDTO user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(user.getRoles())
                .map(x -> new SimpleGrantedAuthority(x.toString()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}

