package com.otl.accommodation.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otl.accommodation.dto.AccommodationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    @Value("${accommodation-key}")
    private String serviceKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public List<AccommodationDTO> getAccommodations() {
        List<AccommodationDTO> accommodations = new ArrayList<>();
        String url = "https://apis.data.go.kr/4510000/GetRestService/getRestInfo?" +
                "serviceKey=" + serviceKey +
                "&pageIndex=10" +
                "&firstIndex=1" +
                "&dataType=JSON";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            accommodations = parseJsonData(response.getBody());

        } catch (Exception e) {
            e.printStackTrace(); // 예외를 로그로 기록하거나 적절히 처리
        }

        return accommodations;
    }

    public List<AccommodationDTO> getAccommodationsByThemeCode(Long themeCode) {
        List<AccommodationDTO> accommodations = new ArrayList<>();
        String url = "https://apis.data.go.kr/4510000/GetRestService/getRestInfo?" +
                "serviceKey=" + serviceKey +
                "&pageIndex=10" +
                "&firstIndex=1" +
                "&dataType=JSON";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            accommodations = parseJsonData(response.getBody());

            accommodations = accommodations.stream()
                    .filter(a -> themeCode.equals(a.getThemeCode()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace(); // 예외를 로그로 기록하거나 적절히 처리
        }

        return accommodations;
    }

    public AccommodationDTO getAccommodationByName(String accommodationName) {
        String url = "https://apis.data.go.kr/4510000/GetRestService/getRestInfo?" +
                "serviceKey=" + serviceKey +
                "&pageIndex=10" +
                "&firstIndex=1" +
                "&dataType=JSON";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            List<AccommodationDTO> accommodations = parseJsonData(response.getBody());

            return accommodations.stream()
                    .filter(a -> accommodationName.equalsIgnoreCase(a.getAccommodationName()))
                    .findFirst()
                    .orElse(null);

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public List<AccommodationDTO> searchAccommodationByName(String searchContent) {
        List<AccommodationDTO> accommodations = new ArrayList<>();
        String url = "https://apis.data.go.kr/4510000/GetRestService/getRestInfo?" +
                "serviceKey=" + serviceKey +
                "&pageIndex=10" +
                "&firstIndex=1" +
                "&dataType=JSON";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            accommodations = parseJsonData(response.getBody());

            accommodations = accommodations.stream()
                    .filter(a -> a.getAccommodationName().toLowerCase().contains(searchContent.toLowerCase()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return accommodations;
    }

    // JSON 데이터 파싱하기 위한 메서드
    private List<AccommodationDTO> parseJsonData(String jsonData) throws IOException {
        List<AccommodationDTO> accommodations = new ArrayList<>();

        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode itemsNode = rootNode.path("data_list");

        if (itemsNode.isArray()) {
            for (JsonNode node : itemsNode) {
                AccommodationDTO accommodationDTO = new AccommodationDTO();
                accommodationDTO.setAccommodationAddress(node.path("address").asText("정보미제공"));
                accommodationDTO.setPhoneNumber(node.path("phone").asText("정보미제공"));
                accommodationDTO.setAccommodationName(node.path("hotelname").asText("정보미제공"));
                accommodationDTO.setAccommodationDescription(node.path("explain").asText("정보미제공"));
                accommodationDTO.setLocalName(node.path("localname").asText("정보미제공"));
                accommodationDTO.setThemeName(node.path("themename").asText("정보미제공"));
                accommodationDTO.setThemeCode(node.path("themecode").asLong(0));
                accommodationDTO.setRoom(node.path("room").asText());
                accommodationDTO.setAccommodationPage(node.path("homepage").asText("없음"));

                accommodations.add(accommodationDTO);
            }
        }

        return accommodations;
    }

}
