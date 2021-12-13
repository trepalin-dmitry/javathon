package ru.filit.mdma.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.filit.mdma.crm.entity.user.User;
import ru.filit.mdma.crm.repository.UserRepository;
import ru.filit.mdma.crm.web.AuthUser;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<User> optionalUser = userRepository.getByUsername(username);
            return new AuthUser(optionalUser.orElseThrow(
                    () -> new UsernameNotFoundException("User '" + username + "' was not found")));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
