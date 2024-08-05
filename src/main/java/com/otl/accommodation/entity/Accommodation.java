package com.otl.accommodation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accommodation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accommodationId;   // 숙소 기본키

    private String accommodationName;   // 숙소명

    private String accommodationAddress;    // 숙소 주소

    private String phoneNumber; // 숙소 번호

    private String themeName;   // 업소 구분

    private String pictureUrl; // 숙소 대표 사진

    public Accommodation(String accommodationName, String accommodationAddress, String themeName, String pictureUrl) {
        this.accommodationName = accommodationName;
        this.accommodationAddress = accommodationAddress;
        this.themeName = themeName;
        this.pictureUrl = pictureUrl;
    }
}
