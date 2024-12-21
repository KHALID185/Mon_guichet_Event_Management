package org.usermicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.usermicroservice.enums.Active;
import java.util.ArrayList;
import java.util.Collection;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class UserDTO {
    private Long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String numberPhone;
    private String password;
    private Active isActive;
    private boolean isEnabled;
    private boolean isAdmin;
    private String confirmationToken;
    private String resetPasswordToken;
    private Collection<RoleDTO> roles = new ArrayList<>();
}
