package com.example.Springsecurity.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {
    private String jwtToken;
}
