package com.otl.accommodation.dto;

import lombok.Data;

@Data
public class AccommodationDTO {
    private String accommodationName;   // 숙소명

    private String accommodationAddress;    // 숙소 주소

    private String accommdationDescription; // 숙소 설명

    private String themeName;   // 업소 구분

    private long pictureId; // 숙소 대표 사진

}
