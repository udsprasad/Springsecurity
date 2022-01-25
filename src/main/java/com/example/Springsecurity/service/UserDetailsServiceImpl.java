package com.example.Springsecurity.service;

import com.example.Springsecurity.DTO.UserDetailsImpl;
import com.example.Springsecurity.DTO.UserDto;
import com.example.Springsecurity.SpringsecurityApplication;
import com.example.Springsecurity.model.User;
import com.example.Springsecurity.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    SpringsecurityApplication springsecurityApplication;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);
        if(!Optional.ofNullable(user).isPresent()){
            throw new UsernameNotFoundException("Could not found user");
        }
        return new UserDetailsImpl(user);
    }

    public User insertUser(UserDto userDto) {
        return userRepo.save(User.builder()
                .password(springsecurityApplication.getEncoder().encode(userDto.getPassword()))
                .userName(userDto.getUserName())
                .enabled(userDto.getEnabled())
                .issueAt(userDto.getIssueAt())
                .role(userDto.getRole()).build());
    }
}
