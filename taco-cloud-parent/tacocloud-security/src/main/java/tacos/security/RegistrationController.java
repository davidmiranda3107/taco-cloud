package tacos.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.User;
import tacos.data.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
       userRepo.save(form.toUser(passwordEncoder));
       return "redirect:/login";
    }

    @Bean
    public CommandLineRunner dataUserLoader(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            repo.save(new User("habuma", encoder.encode("password")));
            repo.save(new User("tacochef", encoder.encode("password")));
        };        
    }
    
}
