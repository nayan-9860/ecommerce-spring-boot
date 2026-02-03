package com.nayan.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    @NotBlank(message = "name is required")
    private String name;

    @Email(message = "invalid email")
    @NotBlank(message = "email is required")
    private String email;

    @Size(min = 6 , message = "Password must be at least 6 characters")
    private String password;
}
