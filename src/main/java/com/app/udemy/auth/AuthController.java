package com.app.udemy.auth;

import com.app.udemy.user.Roles;
import com.app.udemy.user.User;
import com.app.udemy.user.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> SignUp(@RequestBody AuthRegister authRegister, HttpServletResponse response) {
        System.out.println(authRegister);
        return ResponseEntity.ok(service.register(authRegister, response));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> SignUp(@RequestBody AuthLogin authLogin, HttpServletResponse response) {
        try {

            return ResponseEntity.ok(service.login(authLogin, response));
        } catch (Exception err) {
            System.out.println(err.getMessage());
            throw new RuntimeException("user or password is wrong");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logOut(HttpServletResponse response) {
        service.logout(response);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\":\"you are successfully logout\"}");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrent(@AuthenticationPrincipal User user) {
        if (user != null) {
            String roles = user.getRoles()
                    .name()
                    .substring(5)
                    .toLowerCase();

            UserDto userDto = UserDto.builder()
                    .email(user.getEmail())
                    .fullName(user.getFullName())
                    .id(user.getId())
                    .roles(roles)
                    .doc(user.getRoles() == Roles.ROLE_STUDENT
                            ? user.getStudents() : user.getInstructor())
                    .build();
            return ResponseEntity.ok(userDto);
        } else {
            throw new RuntimeException("you are not authenticated");
        }
    }
}
