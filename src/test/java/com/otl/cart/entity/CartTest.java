package com.otl.cart.entity;

import com.otl.cart.repository.CartRepository;
import com.otl.user.dto.UserRegisterFormDTO;
import com.otl.user.entity.User;
import com.otl.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class CartTest {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    public User createUser() {
        UserRegisterFormDTO userRegisterFormDTO = new UserRegisterFormDTO();
        userRegisterFormDTO.setEmail("test@email.com");
        userRegisterFormDTO.setName("홍길동");
        userRegisterFormDTO.setAddress("부산광역시 부산진구 서면역 1번출구");
        userRegisterFormDTO.setPassword("1234");
        return User.createUser(userRegisterFormDTO, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void findCartAndUserTest() {
        User user = createUser();
        userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);

        em.flush();
        em.clear();

        Cart savedCart = cartRepository.findById(cart.getId())
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(savedCart.getUser().getId(), user.getId());
    }
}