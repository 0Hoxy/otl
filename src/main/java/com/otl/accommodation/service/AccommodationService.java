package com.otl.accommodation.service;

import com.otl.accommodation.dto.SearchRequestDTO;
import com.otl.accommodation.entity.Accommodation;
import com.otl.accommodation.entity.AccommodationImg;
import com.otl.accommodation.repository.AccommodationImgRepository;
import com.otl.accommodation.repository.AccommodationRepository;
import com.otl.accommodation.specification.AccommodationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationImgRepository accommodationImgRepository;

    // 숙소 전체 리스트
    public List<Accommodation> getAccommodations() {
        List<Accommodation> accommodations = accommodationRepository.findAll();

        // 각 숙소에 대해 이미지를 로드하여 설정
        for (Accommodation accommodation : accommodations) {
            List<AccommodationImg> images = accommodationImgRepository.findByAccommodation_AccommodationId(accommodation.getAccommodationId());
            accommodation.setAccommodationImgs(images);
        }
        return accommodations;
    }

    // 관리자 숙소 리스트 => 수정 필요
    public Page<Accommodation> getAdminAccommodations(Pageable pageable) {
        Page<Accommodation> accommodations = accommodationRepository.findAll(pageable);

        // 각 숙소에 대해 이미지를 로드하여 설정
        for (Accommodation accommodation : accommodations) {
            List<AccommodationImg> images = accommodationImgRepository.findByAccommodation_AccommodationId(accommodation.getAccommodationId());
            accommodation.setAccommodationImgs(images);
        }
        return accommodations;
    }

    // 숙소 생성
    public Accommodation addAccommodation(String themeName, String accommodationName, String accommodationAddress, String accommodationDescription) {
        Accommodation accommodation = new Accommodation(themeName, accommodationName, accommodationAddress, accommodationDescription);

        accommodation.setThemeName(themeName);
        accommodation.setAccommodationName(accommodationName);
        accommodation.setAccommodationAddress(accommodationAddress);
        accommodation.setAccommodationDescription(accommodationDescription);

        return accommodationRepository.save(accommodation);
    }

    // 숙소 상세페이지
    public Accommodation getAccommodation(long accommodationId) {
        Accommodation accommodation= accommodationRepository.findById(accommodationId).get();

        List<AccommodationImg> images = accommodationImgRepository.findByAccommodation_AccommodationId(accommodation.getAccommodationId());
        accommodation.setAccommodationImgs(images);

        return accommodation;
    }

    // 숙소 카테고리별 리스트
    public List<Accommodation> getAccommodationsByThemeName(String themeName) {
        List<Accommodation> accommodations = accommodationRepository.findByThemeName(themeName);

        // 각 숙소에 대해 이미지를 로드하여 설정
        for (Accommodation accommodation : accommodations) {
            List<AccommodationImg> images = accommodationImgRepository.findByAccommodation_AccommodationId(accommodation.getAccommodationId());
            accommodation.setAccommodationImgs(images);
        }
        return accommodations;
    }

    // 숙소 수정
    public void editAccommodation(long accommodationId,
                                  String themeName,
                                  String accommodationName,
                                  String accommodationAddress,
                                  String accommodationDescription) {

        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new NoSuchElementException("해당 숙소를 찾을 수 없습니다: " + accommodationId));

        accommodation.setThemeName(themeName);
        accommodation.setAccommodationName(accommodationName);
        accommodation.setAccommodationAddress(accommodationAddress);
        accommodation.setAccommodationDescription(accommodationDescription);

        accommodationRepository.save(accommodation);
    }


    // 숙소 삭제
    public void deleteAccommodation(long accommodationId) {
        accommodationRepository.deleteById(accommodationId);
    }

    // 숙소 검색 기능
    public List<Accommodation> searchAccommodation(SearchRequestDTO search) {
        Specification<Accommodation> spec = Specification.where(null);

        if (search.getThemeName() != null && !search.getThemeName().isEmpty()) {
            spec = spec.and(AccommodationSpecification.equalThemeName(search.getThemeName()));
        }

        if (search.getSearchContent() != null && !search.getSearchContent().isEmpty()) {
            spec = spec.and(AccommodationSpecification.likeAccommodationNameOrAddress(search.getSearchContent()));
        }

        if (search.getPeopleCnt() > 0) {
            spec = spec.and(AccommodationSpecification.hasRoomForPeopleCnt(search.getPeopleCnt()));
        }

        List<Accommodation> accommodations = accommodationRepository.findAll(spec);

        // 각 숙소에 대해 이미지를 로드하여 설정
        for (Accommodation accommodation : accommodations) {
            List<AccommodationImg> images = accommodationImgRepository.findByAccommodation_AccommodationId(accommodation.getAccommodationId());
            accommodation.setAccommodationImgs(images);
        }

        return accommodations;
    }

}
