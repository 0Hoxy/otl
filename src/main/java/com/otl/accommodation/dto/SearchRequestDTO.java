package com.otl.accommodation.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchRequestDTO {

    private String searchContent;   // 숙소명

    private String themeName;   // 업소 구분

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Long peopleCnt;
}
