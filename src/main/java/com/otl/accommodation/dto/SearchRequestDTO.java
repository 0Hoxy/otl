package com.otl.accommodation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SearchRequestDTO {

    private String searchContent;   // 숙소명

    private String themeName;   // 업소 구분

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Long peopleCnt;
}
