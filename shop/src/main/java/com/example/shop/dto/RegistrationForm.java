package com.example.shop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationForm {

    @NotBlank(message = "Введите логин")
    @Size(min = 4, max = 50, message = "Логин должен быть от 4 до 50 символов")
    private String username;

    @NotBlank(message = "Введите пароль")
    @Size(min = 6, max = 50, message = "Пароль должен быть от 6 до 50 символов")
    private String password;

    @NotBlank(message = "Введите имя")
    @Size(max = 30, message = "Не более 30 символов")
    private String firstName;

    @NotBlank(message = "Введите фамилию")
    @Size(max = 30, message = "Не более 30 символов")
    private String secondName;

    @NotBlank(message = "Введите email")
    @Email(message = "Некорректный email")
    @Size(max = 100, message = "Не более 100 символов")
    private String email;
}
