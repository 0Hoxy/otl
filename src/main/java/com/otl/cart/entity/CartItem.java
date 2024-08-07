package com.otl.cart.entity;

import com.otl.common.Entity.BaseEntity;
import com.otl.items.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)//하나의 장바구니 안에는 여러개의 상품을 담을 수 있으므로 Many(여러개의 상품)ToOne(장바구니)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY) //장바구니에 담을 상품의 정보도 알아야한다. 하나의 상품은 여러 장바구니의 장바구니 상품으로 담길 수 있으므로 똑같이 Many(여러개의 장바구니)ToOne(상품)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count; //같은 상품을 장바구니에 몇 개 담을지 저장한다.

    public static CartItem createCartItem(Cart cart, Item item, int count) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);
        return cartItem;
    }

    public void addCount(int count) {
        this.count += count;
    }

    public void updateCount(int count) {
         this.count += count;
    }
}
