package org.usermicroservice.dtos;

import lombok.*;
import org.usermicroservice.enums.Active;
import org.usermicroservice.enums.ERole;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class LoginResponseDTO {
    private String email;
    private String password;
    private ERole ERole;
    private Active isActive;
    private boolean isEnabled;
}
