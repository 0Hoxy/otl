package com.otl.accommodation.repository;

import com.otl.accommodation.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    List<Room> findByAccommodation_AccommodationId(long accommodationId);

    Page<Room> findByAccommodation_AccommodationId(long accommodationId, Pageable pageable);

    @Query("SELECT r.accommodation.accommodationId FROM Room r WHERE r.roomId = :roomId")
    long findAccommodationIdByRoomId(@Param("roomId") long roomId);

}
