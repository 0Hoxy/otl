package com.otl.accommodation.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "accommodation")
@Data
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accommodationId;   // 숙소 기본키

    private String accommodationName;   // 숙소명

    private String accommodationAddress;    // 숙소 주소

    private String localName;    // 지역명

    private String phoneNumber; // 숙소 번호

    private String themeName;   // 업소 구분

    private String accommodationPage;   // 숙소 페이지

}
