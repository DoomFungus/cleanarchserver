package kpi.cleanarch.cleanarchserver.controller;

import kpi.cleanarch.cleanarchserver.messages.SignInResponse;
import kpi.cleanarch.cleanarchserver.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("signin")
    public SignInResponse getUsername(){
        return new SignInResponse(authService.signIn());
    }
}
