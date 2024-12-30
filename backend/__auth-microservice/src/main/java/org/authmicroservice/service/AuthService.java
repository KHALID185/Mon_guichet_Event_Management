package org.authmicroservice.service;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.authmicroservice.client.UserServiceClient;
import org.authmicroservice.dto.*;
import org.authmicroservice.enums.CustomerMessageError;
import org.authmicroservice.enums.CustomerMessageValidator;
import org.authmicroservice.exceptions.DataNotValidException;
import org.authmicroservice.exceptions.EmailOrPasswordIncorrectException;
import org.authmicroservice.utils.InputValidatorRegister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {
    private final UserServiceClient userServiceClient;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    public LoginResponseDTO login(LoginRequestDTO request) {
        UserDTO userDTO = userServiceClient.getUserByEmail(request.getEmail()).getBody();
        if (userDTO != null && passwordEncoder.matches(request.getPassword(), userDTO.getPassword())) {
            // Retrieve the UserDetails object for the given username (email)
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());

            // Perform the authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.isAuthenticated()) {
                return LoginResponseDTO.builder()
                        .email(userDTO.getEmail())
                        .accessToken(jwtService.generateToken(request.getEmail(), userDetails))
                        // For the refreshToken, you might need to call a different method,
                        // or pass different parameters to indicate that it is a refresh token
                        .refreshToken(jwtService.generateToken(request.getEmail(), userDetails))
                        .build();
            } else {
                throw new EmailOrPasswordIncorrectException("User is not Authenticated");
            }
        } else {
            throw new EmailOrPasswordIncorrectException("User is not Authenticated");
        }
    }


    /*public LoginResponseDTO login(LoginRequestDTO request) {
        UserDTO userDTO = userServiceClient.getUserByEmail(request.getEmail()).getBody();
        if (userDTO != null && passwordEncoder.matches(request.getPassword(), userDTO.getPassword())) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.isAuthenticated()) {
                return LoginResponseDTO.builder()
                        .email(userDTO.getEmail())
                        .accessToken(jwtService.generateToken(request.getEmail()))
                        .refreshToken(jwtService.generateToken(request.getEmail()))
                        .build();
            } else {
                throw new EmailOrPasswordIncorrectException("User is not Authenticated");
            }
        } else {
            throw new EmailOrPasswordIncorrectException("Email or password is incorrect");
        }
    }*/

    public RegisterResponseDTO register(RegisterRequestDTO request) {
        if (InputValidatorRegister.isValidPassword(request.getPassword()))
            throw new DataNotValidException(CustomerMessageError.PASSWORD_LENGTH_ERROR.getMessage());
        if (!InputValidatorRegister.isValidMoroccanPhoneNumber(request.getNumberPhone()))
            throw new DataNotValidException(CustomerMessageError.PHONE_NUMBER_NOT_VALID.getMessage());
        if (!InputValidatorRegister.isValidEmail(request.getEmail()))
            throw new DataNotValidException(CustomerMessageError.EMAIL_IS_INVALID.getMessage());
        if (InputValidatorRegister.isNull(request.getFirstname()))
            throw new DataNotValidException(CustomerMessageError.FIRSTNAME_IS_REQUIRED.getMessage());
        if (InputValidatorRegister.isNull(request.getLastname()))
            throw new DataNotValidException(CustomerMessageError.LASTNAME_IS_REQUIRED.getMessage());
        if (userServiceClient.existsByEmail(request.getEmail()))
            throw new DataNotValidException(CustomerMessageError.EMAIL_ALREADY_EXIST.getMessage());
        userServiceClient.save(request).getBody();
        // email for validation
        return RegisterResponseDTO.builder()
                .message(CustomerMessageValidator.SAVED_SUCCESSFULLY.getMessage()+" please "
                        + CustomerMessageValidator.CHECK_EMAIL_FOR_VALIDATION_YOUR_MAIL.getMessage())
                .build();
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        return userServiceClient.confirmUserAccount(confirmationToken);
    }

    @Override
    public ResponseEntity<String> handleResetPassword(String email) throws MessagingException {
        return userServiceClient.handleResetPassword(email);
    }

    @Override
    public ResponseEntity<String> handleChangePassword(ChangePasswordDTO changePasswordDTO) {
        if (changePasswordDTO.getToken() == null || changePasswordDTO.getToken().isEmpty())
            throw new DataNotValidException(CustomerMessageError.INVALID_REQUEST.getMessage());
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getMatchPassword()))
            throw new RuntimeException(CustomerMessageError.PASSWORD_MATCH_ERROR.getMessage());
        return userServiceClient.handleChangePassword(changePasswordDTO);
    }

}
