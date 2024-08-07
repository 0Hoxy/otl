package com.otl.accommodation.specification;

import com.otl.accommodation.entity.Accommodation;
import com.otl.accommodation.entity.Room;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class RoomSpecification {
    public static Specification<Room> findRoomForAccommodationAndPeopleCnt(long accommodationId, Long peopleCnt) {
        return (root, query, criteriaBuilder) -> {
            Join<Room, Accommodation> accommodationJoin = root.join("accommodation", JoinType.INNER);

            return criteriaBuilder.and(
                    criteriaBuilder.equal(accommodationJoin.get("accommodationId"), accommodationId),
                    criteriaBuilder.greaterThanOrEqualTo(root.get("roomMaxCnt"), peopleCnt)
            );
        };
    }
}
