package com.example.shop.config;

import com.example.shop.entity.User;
import com.example.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole() != null ? user.getRole().getTitle() : "Клиент")
                    .build();
        };

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/", "/products-page", "/products-jpa-page",
                                "/categories", "/products-jpa").permitAll()

                        // Категории: просмотр — Админист и Менеджер, остальное — только Админист.
                        // Порядок важен: более узкое правило должно стоять выше.
                        .requestMatchers(HttpMethod.GET, "/admin/categories").hasAnyRole("Админист", "Менеджер")
                        .requestMatchers("/admin/categories/**").hasRole("Админист")

                        .requestMatchers("/admin/**").hasAnyRole("Админист", "Менеджер")
                        .requestMatchers("/profile").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/products-jpa-page", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/products-jpa-page")
                        .permitAll()
                );
                
        return http.build();
    }

}
