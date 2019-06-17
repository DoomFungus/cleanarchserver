package kpi.cleanarch.cleanarchserver.repository;

import kpi.cleanarch.cleanarchserver.model.User;

import java.util.Optional;

public interface UserRepository {
    void saveUser(User user);
    Optional<User> getUserByUsername(String username);
    void deleteUserByUsername(String username);
}
