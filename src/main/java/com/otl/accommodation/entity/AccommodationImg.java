package com.otl.accommodation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accommodation_img")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationImg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accommodationImgId;     // 이미지 기본키(id)

    private String accommodationImgName;  // 이미지 파일명

    private String oriaccommodationImgName; // 원본 이미지 파일명

    private String accommodationImgPath;    // 이미지 파일 경로

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation; // 숙소 번호 (외래키)

    public void accommodationImgUpload(String accommodationImgName, String oriaccommodationImgName, String accommodationImgPath) {
        this.accommodationImgName = accommodationImgName;
        this.oriaccommodationImgName = oriaccommodationImgName;
        this.accommodationImgPath = accommodationImgPath;
    }
}
