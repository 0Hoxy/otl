package com.otl.cart.service;

import com.otl.cart.dto.CartItemDto;
import com.otl.cart.entity.Cart;
import com.otl.cart.entity.CartItem;
import com.otl.cart.repository.CartItemRepository;
import com.otl.cart.repository.CartRepository;
import com.otl.items.entity.Item;
import com.otl.items.repository.ItemRepository;
import com.otl.user.entity.User;
import com.otl.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;

    public Long addCart(CartItemDto cartItemDto, String email) {
        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findByEmail(email);

        Cart cart = cartRepository.findByUserId(user.getId());
        if(cart == null) {
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        if (savedCartItem != null) {
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        } else {
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }
}
