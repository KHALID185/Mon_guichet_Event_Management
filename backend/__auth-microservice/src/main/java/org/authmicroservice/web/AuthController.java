package org.authmicroservice.web;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.authmicroservice.dto.*;
import org.authmicroservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {return ResponseEntity.ok(authService.login(request));}

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO request) {return ResponseEntity.ok(authService.register(request));}

    @GetMapping("/confirm-account")
    public ResponseEntity<?> validationEmail(@RequestParam("token") String confirmationToken) {
        return authService.confirmEmail(confirmationToken);
    }

    @PostMapping("/retrieve-password")
    public ResponseEntity<?> handleResetPassword(@RequestParam("email") String email) throws MessagingException {
        return authService.handleResetPassword(email);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> handleChangePassword(@RequestParam("token") String token, @RequestBody ChangePasswordDTO changePasswordDTO) {
        changePasswordDTO.setToken(token);
        authService.handleChangePassword(changePasswordDTO);
        return ResponseEntity.ok("Le mot de passe a été changé avec succès");
    }

}
