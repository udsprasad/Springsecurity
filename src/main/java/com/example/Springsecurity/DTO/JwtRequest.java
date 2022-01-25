package com.example.Springsecurity.DTO;

import lombok.Data;

@Data
public class JwtRequest {
     private String userName;
     private String password;
}
