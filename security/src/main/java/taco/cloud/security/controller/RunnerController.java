package taco.cloud.security.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import taco.cloud.security.user.Role;
import taco.cloud.security.user.User;
import taco.cloud.security.user.UserRepository;

@RestController
public class RunnerController {
    
    public UserRepository userRepo;

    public RunnerController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    */

    @Bean
    public CommandLineRunner dataLoader(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            repo.save(new User("habuma", encoder.encode("password"),  Role.ADMIN));
            repo.save(new User("tacochef", encoder.encode("password"),  Role.ADMIN));
            repo.findAll().forEach(user -> {
                System.out.println("USER " + user.getId() 
                    + " - " + user.getUsername()
                    + " - " + user.getPassword()
                    + " - " + user.getRole());
            });
        };
    }
    
}
