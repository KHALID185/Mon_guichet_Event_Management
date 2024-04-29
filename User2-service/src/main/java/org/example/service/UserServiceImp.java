package org.example.service;

import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;

import org.example.dto.request.EditDto;
import org.example.dto.request.LoginDto;
import org.example.dto.request.SignupDto;
import org.example.dto.response.UserResponseDto;
import org.example.exception.InvalidPasswordException;
import org.example.exception.ResourceNotFoundException;
import org.example.exception.UserAlreadyExistsException;
import org.example.mapper.MappingProfileUser;
import org.example.models.User;
import org.example.utils.HashPassword;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.repository.UserRepository;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor

public class UserServiceImp implements IUserService{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Override
    public UserResponseDto addUser(final SignupDto signupDto) {
        User user = MappingProfileUser.mapToUser(signupDto);
        var exist = this.userRepository.existsUser(user);
        if(exist){
            throw new UserAlreadyExistsException(UserAlreadyExistsException.USER_ALREADY_EXISTS);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var saveUser=this.userRepository.save(user);
        return MappingProfileUser.mapToUserResponseDto(saveUser);
    }



    @Override
    public UserResponseDto getByUsername(String userName) {
        var user= this.userRepository.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User","userName",userName));
        return MappingProfileUser.mapToUserResponseDto(user);
    }

    @Override
    public void deleteById(String id) {
        var user = this.userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",id));
        this.userRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public UserResponseDto getUserById(String id) {
        var user= this.userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",id));
        return MappingProfileUser.mapToUserResponseDto(user);
    }

    @Override
    public UserResponseDto UpdateUser(EditDto editDto) {
        var user = this.userRepository.findByUsername(editDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User","userName", editDto.getUsername()));

        // Hash the new password
//        String hashedNewPass = HashPassword.sha256Hash(editDto.getNewPass());

        // Compare hashed passwords
        if ((Objects.equals(editDto.getPassword(), user.getPassword()))) {
            user.setPassword(editDto.getNewPass());
        } else {
            // Throw an exception or handle the case where passwords don't match
            throw new InvalidPasswordException("Invalid password");
        }

        var savedUser = this.userRepository.save(user);
        return MappingProfileUser.mapToUserResponseDto(savedUser);
    }

    @Override
    public UserResponseDto getUserLogin(LoginDto loginDto) {
//        var pw = HashPassword.sha256Hash(loginDto.getPassword());
        var user = this.userRepository.findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword())
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",loginDto.getUsername()));
        return MappingProfileUser.mapToUserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
            return userRepository.findAll()
                    .stream()
                    .map(MappingProfileUser::mapToUserResponseDto).toList();
    }
}