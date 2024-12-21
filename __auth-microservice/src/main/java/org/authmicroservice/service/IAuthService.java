package org.authmicroservice.service;

import jakarta.mail.MessagingException;
import org.authmicroservice.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IAuthService {

    LoginResponseDTO login(LoginRequestDTO request);

    RegisterResponseDTO register(RegisterRequestDTO request);

    ResponseEntity<?> confirmEmail(String confirmationToken);

    ResponseEntity<String> handleResetPassword(String email) throws MessagingException;

    ResponseEntity<String> handleChangePassword( ChangePasswordDTO changePasswordDTO);

}
