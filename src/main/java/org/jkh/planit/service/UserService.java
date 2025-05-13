package org.jkh.planit.service;

import lombok.RequiredArgsConstructor;
import org.jkh.planit.entity.User;
import org.jkh.planit.exception.UserNotFoundException;
import org.jkh.planit.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    public User getByUserId(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}
