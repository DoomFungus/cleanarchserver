package kpi.cleanarch.cleanarchserver.repository.impl;

import kpi.cleanarch.cleanarchserver.model.User;
import kpi.cleanarch.cleanarchserver.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepositoryHashMap implements UserRepository {
    private static ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    @Override
    public void saveUser(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return Optional.of(users.get(username));
    }

    @Override
    public void deleteUserByUsername(String username) {
        users.remove(username);
    }
}
