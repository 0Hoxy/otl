package com.otl.accommodation.repository;

import com.otl.accommodation.entity.AccommodationImg;
import com.otl.accommodation.entity.RoomImg;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomImgRepository extends CrudRepository<RoomImg, Long> {
    List<RoomImg> findByRoom_RoomId(long roomId);
}
