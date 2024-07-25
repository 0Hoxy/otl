package com.otl.order.entity;

import com.otl.order.constant.OrderStatus;
import com.otl.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne // 주문 여러개 To 회원한명 (한명은 여러 번의 주문을 할 수 있다)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order") //주문 상품은 일대다 매핑. 외래키가 order_item 테이블에 있으므로 연관 관계의 주인은 OrderItem 엔티티이다. Order 엔티티가 주인이 아니므로 "mappedBy" 속성으로 연관 관계의 주인을 설정한다.
                                   //속성 값으로 "order"를 적어준 이유는 OrderItem에 있는 Order에 의해 관리된다는 의미로 해석하면 된다. 즉 연관 관계의 주인의 필드인 order를 mappedBy의 값으로 세팅하면 된다.
    private List<OrderItem> orderItems = new ArrayList<>(); //orderitems 리스트의 주인은 order ?(아직 이해 안됌) && 하나의 주문이 여러 개의 주문 상품을 갖으므로 List 자료형을 사용해서 매핑

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

}
