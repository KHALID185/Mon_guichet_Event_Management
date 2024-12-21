package org.authmicroservice.dto;

import lombok.*;

@Getter
@AllArgsConstructor @NoArgsConstructor @Builder
public class RegisterRequestDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String numberPhone;
    private String password;
}
