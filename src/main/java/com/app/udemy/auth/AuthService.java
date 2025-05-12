package com.app.udemy.auth;

import com.app.udemy.config.JwtService;
import com.app.udemy.instructor.Instructor;
import com.app.udemy.instructor.InstructorService;
import com.app.udemy.student.Students;
import com.app.udemy.student.StudentsService;
import com.app.udemy.user.Roles;
import com.app.udemy.user.User;
import com.app.udemy.user.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final StudentsService studentsService;
    private final InstructorService instructorService;

    public AuthResponse register(AuthRegister authRegister, HttpServletResponse response) {
        Roles role = authRegister.getRoles().equalsIgnoreCase("student")
                ? Roles.ROLE_STUDENT
                : Roles.ROLE_INSTRUCTOR;
        User user = User.builder().fullName(authRegister.getFullName())
                .email(authRegister.getEmail())
                .password(passwordEncoder.encode(authRegister.getPassword()))
                .roles(role)
                .build();
        if (user.getRoles() == Roles.ROLE_INSTRUCTOR) {
            Instructor instructor = Instructor.builder().name(user.getFullName()).build();
            user.setInstructor(instructor);
            instructorService.save(instructor);
        } else {
            Students students = Students.builder().name(user.getFullName())
                    .email(user.getEmail())
                    .build();
            user.setStudents(students);
            studentsService.save(students);
        }
        userRepository.save(user);
        String token = jwtService.generate_token(user);
        setCookie(response, token);
        return AuthResponse.builder().token(token).user(user).build();
    }

    public AuthResponse login(AuthLogin authLogin, HttpServletResponse response) {
        authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                authLogin.getEmail(),
                authLogin.getPassword()
        ));
        var user = userRepository
                .findUserByEmail(authLogin.getEmail()).orElseThrow();
        String token = jwtService.generate_token(user);
        setCookie(response, token);
        return AuthResponse.builder().token(token).user(user).build();
    }

    private void setCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("jwt", token);
        cookie.setSecure(true);
        cookie.setMaxAge(90 * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);
    }

    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);
    }
}
