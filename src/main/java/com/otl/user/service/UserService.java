package com.otl.user.service;

import com.otl.user.entity.User;
import com.otl.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService  implements UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        //엔티티 User가 아닌 Userdetail을 구현하고 있는 User 객체를 반환해준다. User객체를 생성하기 위해서 생성자로 회원의 "이메일", "비밀번호", "role"을 파라미터 넘겨준다.
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();

    }
}
