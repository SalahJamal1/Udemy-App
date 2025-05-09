package com.app.udemy.auth;

import com.app.udemy.user.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegister {
    private String fullName;
    private String password;
    private String email;
    private Roles roles;
}
