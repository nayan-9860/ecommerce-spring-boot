package com.nayan.ecommerce.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponseDto {

    private Long userId;
    private String name;
    private String email;
    private String role;

    private String token;
}
