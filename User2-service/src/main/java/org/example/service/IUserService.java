package org.example.service;



import org.example.dto.request.EditDto;
import org.example.dto.request.LoginDto;
import org.example.dto.request.SignupDto;
import org.example.dto.response.UserResponseDto;

import java.util.List;

public interface IUserService {

    UserResponseDto addUser(SignupDto signupDto);

    UserResponseDto getByUsername(String userName);
    void deleteById(String id);

    UserResponseDto getUserById(String id);

    UserResponseDto UpdateUser(EditDto editDto);

   UserResponseDto getUserLogin(LoginDto loginDto);

    List<UserResponseDto> getAllUsers();
}