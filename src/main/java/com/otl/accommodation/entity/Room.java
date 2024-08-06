package com.otl.accommodation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roomId;    // 객실 번호

    private String roomName;    // 객실명

    private String roomDescription; // 객실 설명

    private String roomPrice;   // 객실 가격

    private String checkIn;     // 체크인 시간

    private String checkOut;    // 체크 아웃 시간

    private long roomMinCnt;    // 최소 수용 인원 수

    private long roomMaxCnt;       // 최대 수용 인원 수

    private String roomImageUrl;    // 객실 사진

    private long accommodationId;   // 숙소 번호 (외래키)

}
