package com.otl.accommodation.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "picture")
@Data
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pictureId;     // 사진 번호

    private String pictureUrl;  // 사진 외부 url
}
