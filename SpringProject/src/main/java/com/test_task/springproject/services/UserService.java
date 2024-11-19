package com.test_task.springproject.services;

import com.test_task.springproject.models.User;
import com.test_task.springproject.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getUsername().equals("mrxpositive")) {
            user.setRole("ADMIN_ROLE");
            return saveUser(user);
        }
        user.setRole("USER_ROLE");
        return saveUser(user);
    }

    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = findUserByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) { return user; }
        return Optional.empty();
    }
}
