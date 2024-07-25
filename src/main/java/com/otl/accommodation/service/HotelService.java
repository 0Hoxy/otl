package com.otl.accommodation.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otl.accommodation.dto.HotelResponseDTO;
import com.otl.accommodation.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public List<HotelResponseDTO> searchHotels(String searchContent,
                                               String checkInDate,
                                               String checkOutDate,
                                               Long adults) {
        // SerpApi를 통해 호텔 검색
//        api key 추가해야함 없앴음
        String url = "https://serpapi.com/search?engine=google_hotels&q=" + searchContent
                + "&check_in_date=" + checkInDate + "&check_out_date="
                + checkOutDate + "&adults=" + adults
                + "&gl=kr&hl=ko" + "&api_key=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        List<HotelResponseDTO> hotelDTOS = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
            JsonNode hotelResults = root.path("properties");

            if (hotelResults.isArray()) {
                for (JsonNode hotel : hotelResults) {
                    HotelResponseDTO dto = new HotelResponseDTO();
                    dto.setHotelName(hotel.path("name").asText());
                    dto.setHotelDescription(hotel.path("description").asText());
                    dto.setCheckInTime(hotel.path("check_in_time").asText());
                    dto.setCheckOutTime(hotel.path("check_out_time").asText());
                    dto.setHotelImageUrl(hotel.path("images").get(0).path("thumbnail").asText());
                    dto.setHotelPrice(hotel.path("rate_per_night").path("lowest").asText());
                    dto.setHotelLink(hotel.path("link").asText());
                    hotelDTOS.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hotelDTOS;
    }
}
