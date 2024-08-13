package com.otl.accommodation.repository;

import com.otl.accommodation.entity.Accommodation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long>, JpaSpecificationExecutor<Accommodation> {

    // 테마별 숙소 가져오기
    List<Accommodation> findByThemeName(String themeName);

    // 숙소 목록 페이징 처리
    Page<Accommodation> findAll(Pageable pageable);
}
