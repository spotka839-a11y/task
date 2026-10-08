package com.example.shop.service;

import com.example.shop.dto.RegistrationForm;
import com.example.shop.entity.Role;
import com.example.shop.entity.User;
import com.example.shop.repository.RoleRepository;
import com.example.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final String DEFAULT_ROLE = "Клиент";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsById(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Регистрация нового пользователя: пароль хешируется, роль — «Клиент».
     */
    @Transactional
    public User register(RegistrationForm form) {
        if (existsByUsername(form.getUsername())) {
            throw new IllegalStateException("Логин уже занят: " + form.getUsername());
        }
        Role role = roleRepository.findByTitle(DEFAULT_ROLE)
                .orElseThrow(() -> new IllegalStateException("В таблице role нет роли '" + DEFAULT_ROLE + "'"));

        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setFirstName(form.getFirstName());
        user.setSecondName(form.getSecondName());
        user.setEmail(form.getEmail());
        user.setRegistrationDate(LocalDate.now());
        user.setRole(role);
        return userRepository.save(user);
    }
}
