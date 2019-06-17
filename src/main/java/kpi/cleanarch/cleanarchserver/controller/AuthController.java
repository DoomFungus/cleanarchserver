package kpi.cleanarch.cleanarchserver.controller;

import kpi.cleanarch.cleanarchserver.service.AuthService;
import kpi.cleanarch.cleanarchserver.service.UserService;
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
    public String getUsername(){
        return authService.signIn();
    }

    @GetMapping("test")
    @PreAuthorize("isAuthenticated()")
    public String test(Principal principal){return "Hello"+principal.toString();}
}
