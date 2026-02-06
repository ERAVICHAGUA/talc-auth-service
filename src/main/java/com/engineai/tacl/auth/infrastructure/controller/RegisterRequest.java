package com.engineai.tacl.auth.infrastructure.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String email;
    private String password;
}
