package com.otl.items.dto;

import com.otl.items.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {
    private String searchDateType; //등록 기준일 조회

    private ItemSellStatus searchSellStatus; //판매상태 조회

    private String searchBy; //상품명, 등록자 아이디와 같이 유형조회

    private String searchQuery = ""; //조회할 검색어 저장 변수, 위의 유형을 저장할 것임
}
