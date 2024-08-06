package com.otl.accommodation.entity;

import jakarta.persistence.*;
import lombok.Data;

//@Table(name = "hotel")
//@Data
@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long hotelId;

    private String hotelName;

    private String hotelLocation;

    private String checkInDate;

    private String checkOutDate;

    private String hotelPrice;

}
