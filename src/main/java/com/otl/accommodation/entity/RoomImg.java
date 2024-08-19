package com.otl.accommodation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "room_img")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomImg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomImgId;     // 이미지 기본키(id)

    private String roomImgName;  // 이미지 파일명

    private String oriroomImgName; // 원본 이미지 파일명

    private String roomImgPath;    // 이미지 파일 경로

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room; // 숙소 번호 (외래키)

    public void roomImgUpload(String roomImgName, String oriroomImgName, String roomImgPath) {
        this.roomImgName = roomImgName;
        this.oriroomImgName = oriroomImgName;
        this.roomImgPath = roomImgPath;
    }
}
