package com.otl.accommodation.specification;

import com.otl.accommodation.entity.Accommodation;
import com.otl.accommodation.entity.Room;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class AccommodationSpecification {
    public static Specification<Accommodation> equalThemeName(String themeName) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("themeName"), themeName));
    }

    public static Specification<Accommodation> likeAccommodationNameOrAddress(String searchContent) {
        return (root, query, criteriaBuilder) -> {

            Predicate namePredicate = criteriaBuilder.like(root.get("accommodationName"), "%" + searchContent + "%");
            Predicate addressPredicate = criteriaBuilder.like(root.get("accommodationAddress"), "%" + searchContent + "%");

            return criteriaBuilder.or(namePredicate, addressPredicate);

        };
    }

    public static Specification<Accommodation> hasRoomForPeopleCnt(Long peopleCnt) {
        return (root, query, criteriaBuilder) -> {
            Join<Accommodation, Room> roomJoin = root.join("rooms", JoinType.LEFT);

            return criteriaBuilder.and(
                    criteriaBuilder.greaterThanOrEqualTo(roomJoin.get("roomMaxCnt"), peopleCnt)
            );
        };
    }
}
