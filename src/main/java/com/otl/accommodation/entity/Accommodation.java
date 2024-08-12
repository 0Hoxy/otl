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
    @Column(name = "accommodation_id")
    private long accommodationId;   // 숙소 기본키

    private String accommodationName;   // 숙소명

    private String accommodationAddress;    // 숙소 주소

    private String accommodationDescription; // 숙소 설명

    private String themeName;   // 업소 구분

    @OneToMany(mappedBy = "accommodation")
    private List<Room> rooms;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.REMOVE)
    private List<AccommodationImg> accommodationImgs;

    public Accommodation(String accommodationName, String accommodationAddress, String accommdationDescription, String themeName) {
        this.accommodationName = accommodationName;
        this.accommodationAddress = accommodationAddress;
        this.accommodationDescription = accommdationDescription;
        this.themeName = themeName;
    }
}
