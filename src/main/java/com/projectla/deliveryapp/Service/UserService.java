package com.projectla.deliveryapp.Service;

import org.springframework.stereotype.Service;

import com.projectla.deliveryapp.Entity.User;
import com.projectla.deliveryapp.Repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
