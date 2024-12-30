package org.authmicroservice.client;

import jakarta.mail.MessagingException;
import org.authmicroservice.dto.ChangePasswordDTO;
import org.authmicroservice.dto.RegisterRequestDTO;
import org.authmicroservice.dto.RegisterResponseDTO;
import org.authmicroservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-microservice")
public interface UserServiceClient {

    @DeleteMapping("/users/{id}")
    void deleteUserById(@PathVariable Long id);

    @GetMapping("/users/")
    ResponseEntity<List<UserDTO>> getAllUsers(@RequestHeader("X-User-Role") String role);

    @PostMapping("/users/register")
    ResponseEntity<RegisterResponseDTO> save(@RequestBody RegisterRequestDTO request);

    @GetMapping("/users/getUserByEmail/{email}")
    ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email);

    @GetMapping("/users/existsByEmail/{email}")
    boolean existsByEmail(@PathVariable String email);

    @GetMapping("/users/confirm-account")
    ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken);

    @PostMapping("/users/retrieve-password")
    ResponseEntity<String> handleResetPassword(@RequestParam("email") String email) throws MessagingException;

    @PostMapping("/users/change-password")
    ResponseEntity<String> handleChangePassword( @RequestBody ChangePasswordDTO changePasswordDTO );

}
