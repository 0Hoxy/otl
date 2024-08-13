package com.otl.cart.repository;

import com.otl.cart.entity.Cart;
import com.otl.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserId(Long userId);

}
