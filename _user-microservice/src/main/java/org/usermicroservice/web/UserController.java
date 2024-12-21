package org.usermicroservice.web;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.usermicroservice.dtos.ChangePasswordDTO;
import org.usermicroservice.dtos.UserDTO;
import org.usermicroservice.entities.User;
import org.usermicroservice.exceptions.InvalidPasswordException;
import org.usermicroservice.exceptions.InvalidTokenException;
import org.usermicroservice.exceptions.UserNotFoundException;
import org.usermicroservice.services.IUserService;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final IUserService iUserService;

   /* @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(iUserService.getAllUsers());
    }
*/

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestHeader("X-User-Role") String role) {
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        }
        return ResponseEntity.ok(iUserService.getAllUsers());
    }


    @GetMapping("/Active")
    public ResponseEntity<List<UserDTO>> getAllUsersActive() {
        return ResponseEntity.ok(iUserService.getAllUsersActive());
    }

    @GetMapping("/InActive")
    public ResponseEntity<List<UserDTO>> getAllUsersInActive() {
        return ResponseEntity.ok(iUserService.getAllUserInActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(iUserService.getUserById(id));
    }

    @PostMapping("/register")
    public void createUser(@RequestBody User user) throws MessagingException {
        iUserService.registerUser(user, false);
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(iUserService.getUserByEmail(email));
    }

   /* @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {

        iUserService.deleteUserById(id);
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            iUserService.deleteUserById(id);
            return ResponseEntity.ok("User with ID " + id + " has been deleted successfully.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with ID " + id + " not found.");
        }
    }

    @GetMapping("/existsByEmail/{email}")
    public boolean existsByEmail(@PathVariable String email) {
        return iUserService.existsByEmail(email);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(iUserService.updateUser(id, user));
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        return iUserService.confirmEmail(confirmationToken);
    }

    @PostMapping("/retrieve-password")
    public ResponseEntity<String> handleResetPassword(@RequestParam("email") String email) throws MessagingException {
        iUserService.resetPassword(email);
        return ResponseEntity.ok("Un email de réinitialisation a été envoyé à " + email);
    }



    @PostMapping("/change-password")
    public ResponseEntity<String> handleChangePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        iUserService.changePassword(changePasswordDTO);
        return ResponseEntity.ok("Le mot de passe a été changé avec succès");
    }
/*
    @PostMapping("/changer-mot-de-passe")
    public ResponseEntity<?> handleChangePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getMatchPassword())) {
            return ResponseEntity.badRequest().body("Les mots de passe ne correspondent pas.");
        }

        try {
            iUserService.changePassword(changePasswordDTO);
            return ResponseEntity.ok("Le mot de passe a été changé avec succès.");
        } catch (InvalidTokenException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalide.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur interne est survenue.");
        }
    }

*/


}
