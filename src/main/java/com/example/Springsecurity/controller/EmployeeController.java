package com.example.Springsecurity.controller;

import com.example.Springsecurity.DTO.JwtRequest;
import com.example.Springsecurity.DTO.JwtResponse;
import com.example.Springsecurity.DTO.UserDto;
import com.example.Springsecurity.model.User;
import com.example.Springsecurity.service.UserDetailsServiceImpl;
import com.example.Springsecurity.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EmployeeController {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/")
    public String getEmployee(){
        return "Hai";
    }

//    payload
//    {"userName":"prasad",
//            "password":"ram@1234",
//            "role":"admin",
//            "enabled":"true",
//            "issueAt":"2017-01-13"}

    @PostMapping("/user")
    public ResponseEntity<User> insertUser(@RequestBody @Valid UserDto userDto){
        return ResponseEntity.ok(userDetailsServiceImpl.insertUser(userDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> getJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(jwtRequest.getUserName());
        String token = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok().body(JwtResponse.builder().jwtToken(token).build());
    }
}
