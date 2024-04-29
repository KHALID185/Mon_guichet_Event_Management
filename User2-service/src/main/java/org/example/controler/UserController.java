package org.example.controler;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.dto.request.EditDto;
import org.example.dto.request.LoginDto;
import org.example.dto.request.SignupDto;
import org.example.dto.response.UserResponseDto;
import org.example.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value= "/api/v1/user")
public class UserController {

    private final IUserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> addUser(@Valid @RequestBody SignupDto signupDto) {
        var newUser=this.userService.addUser(signupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/username")
    public ResponseEntity<UserResponseDto> getByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/password")
    public ResponseEntity<UserResponseDto> UpdateUser(@RequestBody EditDto editDto) {
        return ResponseEntity.ok(userService.UpdateUser(editDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> getUserLogin(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(userService.getUserLogin(loginDto));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
