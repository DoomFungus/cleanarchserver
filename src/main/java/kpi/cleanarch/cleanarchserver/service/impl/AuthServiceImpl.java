package kpi.cleanarch.cleanarchserver.service.impl;

import kpi.cleanarch.cleanarchserver.model.User;
import kpi.cleanarch.cleanarchserver.security.JwtProvider;
import kpi.cleanarch.cleanarchserver.service.AuthService;
import kpi.cleanarch.cleanarchserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private static Integer usernameSequence = 0;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserService userService, AuthenticationManager authenticationManager,
                           JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String signIn() {
        String username = usernameSequence.toString();
        usernameSequence++;
        User user = userService.saveUser(username,
                passwordEncoder.encode(username));
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
                username));
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        return jwtProvider.createToken(user.getUsername());
    }
}
