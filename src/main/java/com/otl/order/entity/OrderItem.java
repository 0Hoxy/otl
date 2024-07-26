package com.otl.order.entity;

import com.otl.common.Entity.BaseEntity;
import com.otl.items.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//BaseEntity를 상속 받아서 시간 관련 코드를 이용하고 있음
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

}
