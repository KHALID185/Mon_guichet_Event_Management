package org.example.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class SignupDto {

    @NotEmpty(message = "Name can not be null or empty")
    @Size(min = 3, max = 50, message = "Full name must be between 3 and 50 characters")
    private String fullName;

    @NotBlank(message = "Username must not be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotNull(message = "Password must not be null")
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
    private String password;

    @NotEmpty(message = "Email adress can not be null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @NotEmpty(message = "Gender can not be null or empty")
    private String gender;

    @Pattern(regexp = "^\\+(\\d{10,15})$|^0(\\d{9,15})$", message = "Invalid phone number format")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    private String phone;


}
