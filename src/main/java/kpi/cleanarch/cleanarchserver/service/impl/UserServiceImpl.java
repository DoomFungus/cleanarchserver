package kpi.cleanarch.cleanarchserver.service.impl;

import kpi.cleanarch.cleanarchserver.model.User;
import kpi.cleanarch.cleanarchserver.repository.UserRepository;
import kpi.cleanarch.cleanarchserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(s).orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }

    @Override
    public User saveUser(String username, String password){
        User user = new User(username, password);
        userRepository.saveUser(user);
        return user;
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
    }
}
