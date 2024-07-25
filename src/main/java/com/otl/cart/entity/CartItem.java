package com.otl.cart.entity;

import com.otl.items.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne //하나의 장바구니 안에는 여러개의 상품을 담을 수 있으므로 Many(여러개의 상품)ToOne(장바구니)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne //장바구니에 담을 상품의 정보도 알아야한다. 하나의 상품은 여러 장바구니의 장바구니 상품으로 담길 수 있으므로 똑같이 Many(여러개의 장바구니)ToOne(상품)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count; //같은 상품을 장바구니에 몇 개 담을지 저장한다.
}
