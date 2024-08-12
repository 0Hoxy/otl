package com.otl.accommodation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Column(length = 1000)
    private String roomDescription; // 객실 설명

    private String roomPrice;   // 객실 가격

    private String checkIn;     // 체크인 시간

    private String checkOut;    // 체크 아웃 시간

    private long roomMinCnt;    // 최소 수용 인원 수

    private long roomMaxCnt;       // 최대 수용 인원 수

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation; // 숙소 번호 (외래키)

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<RoomImg> roomImgs;

    public Room(String roomName, String roomDescription, String roomPrice, String checkIn, String checkOut, long roomMinCnt, long roomMaxCnt) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomPrice = roomPrice;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomMinCnt = roomMinCnt;
        this.roomMaxCnt = roomMaxCnt;
    }
}
