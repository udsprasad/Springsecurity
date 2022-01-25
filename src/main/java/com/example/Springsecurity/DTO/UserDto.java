package com.example.Springsecurity.DTO;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UserDto {
    @NotNull(message = "should not be null")
    @Size(min=5,max=10,message = "username should be in between 5 to 10")
    private String userName;

    @NotNull(message = "should not be null")
    @Size(min=8,message = "password should be equal or greater than 8")
    private String password;

    private String role;

    private String enabled;

    private LocalDate issueAt;
}
