package com.otl.accommodation.dto;

import lombok.Data;

@Data
public class HotelResponseDTO {

    private String hotelName;

    private String hotelDescription;

    private String hotelImageUrl;

    private String hotelLocation;

    private String checkInTime;

    private String checkOutTime;

    private String hotelPrice;

    private String hotelLink;

}
