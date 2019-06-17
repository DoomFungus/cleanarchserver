package kpi.cleanarch.cleanarchserver.service;

import kpi.cleanarch.cleanarchserver.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User saveUser(String username, String password);

    void deleteUser(String username);
}
