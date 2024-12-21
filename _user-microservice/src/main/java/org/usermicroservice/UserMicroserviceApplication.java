package org.usermicroservice;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.usermicroservice.entities.Role;
import org.usermicroservice.entities.User;
import org.usermicroservice.enums.Active;
import org.usermicroservice.enums.ERole;
import org.usermicroservice.repositories.RoleRepository;
import org.usermicroservice.services.IUserService;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class UserMicroserviceApplication {
    private final IUserService userService;
    private final RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(UserMicroserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            Role adminRole = new Role(ERole.ADMIN);
            Role userRole = new Role(ERole.USER);
            roleRepository.saveAll(List.of(adminRole, userRole));

            // Register an admin user
            User adminUser = User.builder()
                    .firstname("khalid")
                    .lastname("taouti")
                    .email("khalid99.course@gmail.com")
                    .numberPhone("0611291725")
                    .password("khalid9898") // This should ideally be encoded
                    .roles(Collections.singletonList(adminRole))
                    .isActive(Active.ACTIVE)
                    .build();

            userService.registerUser(adminUser, true); // Assuming true means is enabled

            // Register a regular user
//            User regularUser = User.builder()
//                    .firstname("ismail")
//                    .lastname("lrd")
//                    .email("khalid.1998.taouti@gmail.com")
//                    .numberPhone("0688221122")
//                    .password("qwerty") // This should ideally be encoded
//                    .roles(Collections.singleton(userRole))
//                    .isActive(Active.ACTIVE)
//                    .build();

//            userService.registerUser(regularUser, false); // Assuming true means is enabled

            // Optionally log the users
            System.out.println(adminUser);
//            System.out.println(regularUser);
        };
    }
}
