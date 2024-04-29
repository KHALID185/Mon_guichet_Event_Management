package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Enum.UserRole;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserResponseDto {
    private Long id;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String gender;
    private String phone;
    private UserRole role;
}
