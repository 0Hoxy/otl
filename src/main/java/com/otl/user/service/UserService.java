package com.otl.user.service;

import com.otl.user.entity.User;
import com.otl.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;

    public User joinUser(User user) {
        validateUser(user);
        return userRepository.save(user);
    }

    private void validateUser(User user) {
        User findUser = userRepository.findByEmail(user.getEmail());
        if (findUser != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
