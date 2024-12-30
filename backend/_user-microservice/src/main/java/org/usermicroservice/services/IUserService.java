package org.usermicroservice.services;

import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.usermicroservice.dtos.ChangePasswordDTO;
import org.usermicroservice.dtos.UserDTO;
import org.usermicroservice.entities.User;
import org.usermicroservice.enums.ERole;

import java.util.List;

public interface IUserService {
    List<UserDTO> getAllUsers();
    List<UserDTO> getAllUsersActive();
    List<UserDTO> getAllUserInActive();
    //void registerUser(User user) throws MessagingException;
    void registerUser(User user, boolean isAdmin) throws MessagingException;
    UserDTO getUserById(Long id);
    UserDTO getUserByEmail(String email);
    void deleteUserById(Long id);
    UserDTO updateUser(Long id,User user);
    boolean existsByEmail(String email);
    ResponseEntity<?> confirmEmail(String confirmationToken);
    void resetPassword(String email) throws MessagingException;
    void changePassword(ChangePasswordDTO dto);
    void addRoleToUserByEmail(ERole eRole, String email);
}
