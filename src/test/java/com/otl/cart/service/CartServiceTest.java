package com.otl.cart.service;

import com.otl.cart.dto.CartItemDto;
import com.otl.cart.entity.CartItem;
import com.otl.cart.repository.CartItemRepository;
import com.otl.items.constant.ItemSellStatus;
import com.otl.items.entity.Item;
import com.otl.items.repository.ItemRepository;
import com.otl.user.entity.User;
import com.otl.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class CartServiceTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CartService cartService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartItemRepository cartItemRepository;


    public Item saveItem() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setStockNumber(100);
        return itemRepository.save(item);
    }

    public User saveUser() {
        User user = new User();
        user.setEmail("test@test.com");
        return userRepository.save(user);
    }

    @Test
    @DisplayName("장바구니 담기 테스트")
    public void addCart() {
        Item item = saveItem();
        User user = saveUser();

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCount(5);
        cartItemDto.setItemId(item.getId());

        Long cartItemId = cartService.addCart(cartItemDto, user.getEmail());

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);

        assertEquals(item.getId(), cartItem.getItem().getId());
        assertEquals(cartItemDto.getCount(), cartItem.getCount());
    }

}