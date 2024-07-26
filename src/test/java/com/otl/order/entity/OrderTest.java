package com.otl.order.entity;

import com.otl.items.constant.ItemSellStatus;
import com.otl.items.entity.Item;
import com.otl.items.repository.ItemRepository;
import com.otl.order.repository.OrderItemRepository;
import com.otl.order.repository.OrderRepository;
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
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @PersistenceContext
    EntityManager em;

    public Item createItem() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품1");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return item;
    }

    @Test
    @DisplayName("영속성 전이 테스트") //CasCade Test
    public void cascadeTest() {
        Order order = new Order();

        for (int i = 0; i < 3; i++) {
            Item item = this.createItem();
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        orderRepository.saveAndFlush(order); //flush: 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영
        em.clear();

        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(3, savedOrder.getOrderItems().size());
    }

    public Order createOrder() {
        Order order = new Order();
        for (int i = 0; i < 3; i++) {
            Item item = createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        User user = new User();
        userRepository.save(user);

        order.setUser(user);
        orderRepository.save(order);
        return order;
    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemoveTest() {
        Order order = this.createOrder();
        order.getOrderItems().remove(0); // order 엔티티에서 관리하고 있는 orderItem 리스트의 인덱스 0번 요소를 제거한다.
        em.flush();

    }

    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest() {
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);

        System.out.println("Order class : " + orderItem.getOrder().getClass());
        System.out.println("=================================");
        orderItem.getOrder().getOrderDate();
        System.out.println("=================================");
    }
}