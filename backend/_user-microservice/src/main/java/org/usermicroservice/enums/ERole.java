package org.usermicroservice.enums;

import io.jsonwebtoken.Claims;

import javax.management.relation.Role;
import java.util.List;

public enum ERole {
    ADMIN, USER;

    /*private boolean isAdmin(Claims claims) {
        String roleClaim = claims.get("role", String.class);
        return ERole.ADMIN.name().equals(roleClaim);
    }

    private boolean isUser(Claims claims) {
        String roleClaim = claims.get("role", String.class);
        return ERole.USER.name().equals(roleClaim);
    }*/

    private boolean isAdmin(Claims claims) {
        List<String> roles = claims.get("roles", List.class); // Assuming roles are stored in a list
        return roles != null && roles.contains(ERole.ADMIN.name());
    }

    private boolean isUser(Claims claims) {
        List<String> roles = claims.get("roles", List.class); // Assuming roles are stored in a list
        return roles != null && roles.contains(ERole.USER.name());
    }
}
