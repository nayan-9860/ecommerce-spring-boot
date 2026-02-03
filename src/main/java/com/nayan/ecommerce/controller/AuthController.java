package com.nayan.ecommerce.controller;

import com.nayan.ecommerce.dto.AuthResponseDto;
import com.nayan.ecommerce.dto.LoginRequestDto;
import com.nayan.ecommerce.dto.RegisterRequestDto;
import com.nayan.ecommerce.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto request){

        AuthResponseDto response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request){

        AuthResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
