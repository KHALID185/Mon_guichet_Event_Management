package org.example;

import org.example.dto.request.SignupDto;
import org.example.service.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
public class UserServiceApplication2 {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication2.class, args);

    }

    @Bean
     CommandLineRunner start(IUserService iUserService) {
        return args ->{
            SignupDto signupDto = new SignupDto("achraflamsahel","khalid","azert123","achraf@gmail.com","homme","0621403650");
            iUserService.addUser(signupDto);
        };
    }
}
