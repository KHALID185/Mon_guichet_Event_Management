package org.authmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class LoginResponseDTO {
    private String email;
    private String accessToken;
    private String refreshToken;
}
