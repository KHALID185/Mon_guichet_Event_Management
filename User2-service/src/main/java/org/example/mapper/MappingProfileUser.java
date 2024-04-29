package org.example.mapper;

import org.example.dto.request.EditDto;
import org.example.dto.request.SignupDto;
import org.example.dto.response.UserResponseDto;
import org.example.models.User;
import org.modelmapper.ModelMapper;

public class MappingProfileUser {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static User mapToUser(SignupDto signupDto) {
        return modelMapper.map(signupDto, User.class);
    }


    public static User mapToUser(UserResponseDto userResponseDto) {
        return modelMapper.map(userResponseDto, User.class);
    }

    public static User mapToUser(EditDto editDto) {
        return modelMapper.map(editDto, User.class);
    }

    public static UserResponseDto mapToUserResponseDto(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }

    public static EditDto mapToEditDto(User user) {
        return modelMapper.map(user, EditDto.class);
    }

}
