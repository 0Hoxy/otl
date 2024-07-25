package com.otl.accommodation.dto;

import lombok.Data;

@Data
public class AccommodationDTO {

    private String accommodationName;   // 숙소명

    private String accommodationAddress;    // 숙소 주소

    private String accommodationDescription;    // 숙소 설명

    private String localName;    // 지역명

    private String phoneNumber; // 숙소 번호

    private String themeName;   // 업소 구분

    private String accommodationPage;   // 숙소 페이지

    private String room;  // 객실 수

    private long themeCode;
}
