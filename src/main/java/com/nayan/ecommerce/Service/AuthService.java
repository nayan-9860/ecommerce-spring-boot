package com.nayan.ecommerce.service;


import com.nayan.ecommerce.dto.AuthResponseDto;
import com.nayan.ecommerce.dto.LoginRequestDto;
import com.nayan.ecommerce.dto.RegisterRequestDto;

public interface AuthService {

    AuthResponseDto register(RegisterRequestDto request);

    AuthResponseDto login(LoginRequestDto request);
}
