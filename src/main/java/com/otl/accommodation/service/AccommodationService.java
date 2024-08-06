package com.otl.accommodation.service;

import com.otl.accommodation.entity.Accommodation;
import com.otl.accommodation.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    // 숙소 전체 리스트
    public List<Accommodation> getAccommodations() {
        return accommodationRepository.findAll();
    }

    // 숙소 생성
    public void addAccommodation(String themeName,  String accommodationName, String accommodationAddress, String pictureUrl) {
        Accommodation accommodation = new Accommodation(themeName, accommodationName, accommodationAddress, pictureUrl);

        accommodation.setThemeName(themeName);
        accommodation.setAccommodationName(accommodationName);
        accommodation.setAccommodationAddress(accommodationAddress);
        accommodation.setPictureUrl(pictureUrl);

        accommodationRepository.save(accommodation);
    }

    // 숙소 상세페이지
    public Accommodation getAccommodation(long accommodationId) {
        return accommodationRepository.findById(accommodationId).get();
    }

    // 숙소 카테고리별 리스트
    public List<Accommodation> getAccommodationsByThemeName(String themeName) {
        return accommodationRepository.findByThemeName(themeName);
    }

    // 숙소 수정
    public void editAccommodation(long accommodationId, String themeName,  String accommodationName, String accommodationAddress, String pictureUrl) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).get();

        accommodation.setThemeName(themeName);
        accommodation.setAccommodationName(accommodationName);
        accommodation.setAccommodationAddress(accommodationAddress);
        accommodation.setPictureUrl(pictureUrl);

        accommodationRepository.save(accommodation);
    }

    // 숙소 삭제
    public void deleteAccommodation(long accommodationId) {
        accommodationRepository.deleteById(accommodationId);
    }

}
