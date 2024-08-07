package com.otl.items.entity;

import com.otl.common.Entity.BaseEntity;
import com.otl.items.constant.ItemSellStatus;
import com.otl.items.dto.ItemFormDto;
import com.otl.items.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String itemNm;

    @Column(nullable = false, name = "price")
    private int price;

    @Column(nullable = false)
    private int stockNumber;

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    public void updateItem(ItemFormDto itemFormDto) {
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber; //주문 후 남은 재고 수량
        if(restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량: " + this.stockNumber + ")"); //재고가 주문수량보다 작을경우 예외 발생
        }
        this.stockNumber = restStock; //주문 후 남은 재고 수량을 상품의 현재 재고 값으로 할당한다.
    }

    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber;
    }
}
