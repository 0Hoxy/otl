package com.otl.accommodation.repository;

import com.otl.accommodation.entity.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    List<Room> findByAccommodationId(long accommodationId);

    @Query("SELECT r.accommodationId FROM Room r WHERE r.roomId = :roomId")
    long findAccommodationIdByRoomId(@Param("roomId") long roomId);
}
