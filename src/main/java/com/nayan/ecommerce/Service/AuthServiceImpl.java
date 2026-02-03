package com.nayan.ecommerce.service;

import com.nayan.ecommerce.dto.AuthResponseDto;
import com.nayan.ecommerce.dto.LoginRequestDto;
import com.nayan.ecommerce.dto.RegisterRequestDto;
import com.nayan.ecommerce.entity.User;
import com.nayan.ecommerce.entity.enums.UserRole;
import com.nayan.ecommerce.exception.BadRequestException;
import com.nayan.ecommerce.repository.UserRepository;
import com.nayan.ecommerce.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @Override
    public AuthResponseDto register(RegisterRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("email is already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);

        userRepository.save(user);

        return AuthResponseDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getId(),
                user.getEmail() ,
                "ROLE_" + user.getRole().name());

        return AuthResponseDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .token(token)
                .build();
    }

}

