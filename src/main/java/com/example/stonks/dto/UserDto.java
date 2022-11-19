package com.example.stonks.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    //@NotEmpty
    private String username;
    //@NotEmpty(message = "Email should not be empty")
    //@Email
    private String email;
    //@NotEmpty(message = "Password should be empty")
    private String password;
}
