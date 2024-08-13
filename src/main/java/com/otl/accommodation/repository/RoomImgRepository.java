package com.otl.accommodation.repository;

import com.otl.accommodation.entity.AccommodationImg;
import com.otl.accommodation.entity.RoomImg;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomImgRepository extends CrudRepository<RoomImg, Long> {

    // 객실 ID 에 해당하는 이미지 정보
    List<RoomImg> findByRoom_RoomId(long roomId);
}
