package com.engineai.tacl.auth.infrastructure.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
}