package com.otl.accommodation.repository;

import com.otl.accommodation.entity.AccommodationImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationImgRepository extends JpaRepository<AccommodationImg, Long> {
    List<AccommodationImg> findByAccommodation_AccommodationId(long accommodationId);
}
