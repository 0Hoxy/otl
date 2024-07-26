package com.otl.items.dto;

import com.otl.items.constant.ItemSellStatus;
import com.otl.items.entity.Item;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명을 입력해주세요.")
    private String itemNm;

    @NotBlank(message = "가격을 입력해주세요.")
    private Integer price;

    @NotBlank(message = "상품 설명을 입력해주세요.")
    private String itemDetail;

    @NotBlank(message = "재고를 입력해주세요.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>(); //상품 정보를 저장하는 리스트

    private List<Long> itemImgIds = new ArrayList<>(); //상품의 이미지를 저장하는 리스트

    private static ModelMapper modelMapper = new ModelMapper();


    //modelMapper를 이용하여 엔티티 객체와 DTO 객체 간의 데이터를 복사하여 복사한 객체를 반환해주는 메소드
    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }

}
