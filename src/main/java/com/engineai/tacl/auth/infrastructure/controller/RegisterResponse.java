package com.engineai.tacl.auth.infrastructure.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResponse {

    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private Integer age;
    private String email;
    private String status;
}