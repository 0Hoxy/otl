package com.otl.accommodation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    private String accommdationDescription; // 숙소 설명

    private String themeName;   // 업소 구분

    private String pictureUrl; // 숙소 대표 사진

    @OneToMany(mappedBy = "accommodation")
    private List<Room> rooms;

    public Accommodation(String accommodationName, String accommodationAddress, String accommdationDescription, String themeName, String pictureUrl) {
        this.accommodationName = accommodationName;
        this.accommodationAddress = accommodationAddress;
        this.accommdationDescription = accommdationDescription;
        this.themeName = themeName;
        this.pictureUrl = pictureUrl;
    }
}
