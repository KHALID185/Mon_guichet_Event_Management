package org.usermicroservice.services;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.usermicroservice.dtos.ChangePasswordDTO;
import org.usermicroservice.dtos.UserDTO;
import org.usermicroservice.emails.IMailService;
import org.usermicroservice.entities.Role;
import org.usermicroservice.entities.User;
import org.usermicroservice.enums.Active;
import org.usermicroservice.enums.CustomerEmailMessage;
import org.usermicroservice.enums.CustomerMessageError;
import org.usermicroservice.enums.ERole;
import org.usermicroservice.exceptions.DataNotValidException;
import org.usermicroservice.exceptions.RoleNotFoundException;
import org.usermicroservice.exceptions.UserNotFoundException;
import org.usermicroservice.mappers.UserMapper;
import org.usermicroservice.repositories.RoleRepository;
import org.usermicroservice.repositories.UserRepository;
import org.usermicroservice.utils.TokenGenerator;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final IMailService iMailService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public List<UserDTO> getAllUsers() {

        return UserMapper.usersToUsersDto(userRepository.findAll());
    }

    @Override
    public List<UserDTO> getAllUsersActive() {
        return UserMapper.usersToUsersDto(userRepository.findAll()
                .stream()
                .filter(user -> user.getIsActive().equals(Active.ACTIVE))
                .collect(Collectors.toList()));
    }

    @Override
    public List<UserDTO> getAllUserInActive() {
        return UserMapper.usersToUsersDto(userRepository.findAll()
                .stream()
                .filter(user -> user.getIsActive().equals(Active.INACTIVE))
                .collect(Collectors.toList()));
    }


   /* public void registerUser(User user) throws MessagingException {
        log.info("Creating new user with email : {}", user.getEmail());
        Role role= roleRepository.findByRole(ERole.valueOf(ERole.ADMIN.name())).orElseThrow(
                () -> new RoleNotFoundException(CustomerMessageError.USER_NOT_FOUND_WITH_EMAIL_EQUALS.getMessage() + user.getEmail()));
        User toSave = User.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .numberPhone(user.getNumberPhone())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(List.of(role))
                .isActive(Active.ACTIVE)
                .isEnabled(false)
                .confirmationToken(TokenGenerator.generateToken())
                .build();
        userRepository.save(toSave);
        iMailService.sendConfirmationEmail(toSave, "emailmicroservice1994@gmail.com");
        ResponseEntity.ok("Verify email by the link sent on your email address");
    }*/
   @Override
    public void registerUser(User user, boolean isAdmin) throws MessagingException {
        Role role = roleRepository.findByRole(isAdmin ? ERole.ADMIN : ERole.USER)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
        user.setRoles(Collections.singleton(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsActive(Active.ACTIVE);
        user.setEnabled(false);  // Assume users must verify email to enable
        user.setConfirmationToken(TokenGenerator.generateToken());
        userRepository.save(user);
        iMailService.sendConfirmationEmail(user, user.getEmail());
        ResponseEntity.ok("Verify email by the link sent to your email address");
    }


    @Override
    public UserDTO getUserById(Long id) {
        log.info("Fetching user by id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        CustomerMessageError.USER_NOT_FOUND_WITH_ID_EQUALS.getMessage() + id));
        return UserMapper.userToDto(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new
                        UserNotFoundException(
                        CustomerMessageError.USER_NOT_FOUND_WITH_EMAIL_EQUALS.getMessage() + email));
        return UserMapper.userToDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        CustomerMessageError
                                .USER_NOT_FOUND_WITH_ID_EQUALS.getMessage() + id));
        //user.setEmail("###" + user.getEmail());
        user.setIsActive(Active.INACTIVE);
        userRepository.save(user);
    }

    @Override
    public UserDTO updateUser(Long id, User user) {
        log.info("Updating user with id: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        CustomerMessageError.USER_NOT_FOUND_WITH_ID_EQUALS.getMessage() + id));
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setNumberPhone(user.getNumberPhone());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setIsActive(Active.ACTIVE);
        User updatedUser = userRepository.save(existingUser);
        log.info("User with id {} updated successfully", id);
        return UserMapper.userToDto(updatedUser);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override

    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        User user = userRepository.findByConfirmationToken(confirmationToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid token!"));
        user.setVerifiedAt(new Date());
        user.setEnabled(true);
        userRepository.save(user);

        // Creating a map to hold the response data
        Map<String, String> response = new HashMap<>();
        response.put("message", "Your Email confirmed successfully.");

        // Returning the map as a JSON response
        return ResponseEntity.ok(response);
    }

/*

    @Override
    public void resetPassword(String email) throws MessagingException {
        if (email == null) throw new
                DataNotValidException(CustomerMessageError.EMAIL_IS_REQUIRED.getMessage());
        User user = userRepository.findByEmail(email.toLowerCase()).orElse(null);
        if (user != null  && user.getVerifiedAt() != null && user.isEnabled()) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, 24);
            Date expiryDate = calendar.getTime();
            user.setResetPasswordTokenExpiryDate(expiryDate);
            user.setResetPasswordToken(TokenGenerator.generateToken());
            updateUser(user.getUserId(), user);
            UserDTO userDTO = UserMapper.userToDto(user);
            iMailService.sendResetPasswordMail(user.getEmail(),
                    CustomerEmailMessage.RESET_PASSWORD_SUBJECT.getMessage(),userDTO);
        } else {
            throw new RuntimeException("No account found with this email address!");
        }
    }
*/

    @Override
    public void resetPassword(String email) throws MessagingException {
        log.info("Initiating password reset for email: {}", email);
        if (email == null) throw new DataNotValidException(CustomerMessageError.EMAIL_IS_REQUIRED.getMessage());
        User user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new RuntimeException("No account found with this email address!"));

        if (user.getVerifiedAt() != null && user.isEnabled()) {
            String token = TokenGenerator.generateToken();
            log.info("Generated reset password token: {}", token);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, 24);
            Date expiryDate = calendar.getTime();
            user.setResetPasswordTokenExpiryDate(expiryDate);
            user.setResetPasswordToken(token);
            userRepository.save(user); // Directly save the user repository here
            log.info("User reset token and expiry date updated");

            UserDTO userDTO = UserMapper.userToDto(user);
            iMailService.sendResetPasswordMail(user.getEmail(), CustomerEmailMessage.RESET_PASSWORD_SUBJECT.getMessage(), userDTO);
            log.info("Reset password email sent");
        } else {
            throw new RuntimeException("Account not verified or not enabled!");
        }
    }

    @Override
    public void changePassword(ChangePasswordDTO dto) {
        log.info("Change password : {}","********");
        User appUser = userRepository.findByResetPasswordToken(dto.getToken()).orElseThrow();
        if (appUser != null) {
            if (appUser.getResetPasswordTokenExpiryDate() != null &&
                    appUser.getResetPasswordTokenExpiryDate().before(new Date())) {
                throw new RuntimeException("Le jeton de réinitialisation de mot de passe a expiré.");
            }
            appUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            appUser.setResetPasswordToken(null);
            appUser.setResetPasswordTokenExpiryDate(null);
            updateUser(appUser.getUserId(), appUser);
            UserMapper.userToDto(appUser);
        }
    }

    @Override
    public void addRoleToUserByEmail(ERole eRole, String email) {
        log.info("Add Role to  user with email : {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(
                CustomerMessageError.USER_NOT_FOUND_WITH_EMAIL_EQUALS.getMessage() + email
        ));
        Role role = roleRepository.findByRole(eRole).orElseThrow(() -> new RoleNotFoundException(
                CustomerMessageError.USER_NOT_FOUND_WITH_EMAIL_EQUALS.getMessage() + eRole
        ));
        user.getRoles().add(role);
    }

}


