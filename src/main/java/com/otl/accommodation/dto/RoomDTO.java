package com.otl.accommodation.dto;

import com.otl.accommodation.entity.Accommodation;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class RoomDTO {
    private long rootId;    // 객실 번호

    private String roomName;    // 객실명

    private String roomPrice;   // 객실 가격

    private String checkIn;     // 체크인 시간

    private String checkOut;    // 체크 아웃 시간

    private long roomMinCnt;       // 최소 수용 인원 수

    private long roomMaxCnt;       // 최대 수용 인원 수

}
