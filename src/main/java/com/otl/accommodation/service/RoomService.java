package com.otl.accommodation.service;

import com.otl.accommodation.entity.Accommodation;
import com.otl.accommodation.entity.Room;
import com.otl.accommodation.entity.RoomImg;
import com.otl.accommodation.repository.RoomImgRepository;
import com.otl.accommodation.repository.RoomRepository;
import com.otl.accommodation.specification.RoomSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    private final RoomImgRepository roomImgRepository;

    public Room addRoom(Accommodation accommodation, String roomName, String roomDescription, String roomPrice, String checkIn, String checkOut, long roomMinCnt, long roomMaxCnt) {
        Room room = new Room(roomName, roomDescription, roomPrice, checkIn, checkOut, roomMinCnt, roomMaxCnt);
        room.setAccommodation(accommodation);

        return roomRepository.save(room);
    }

    public List<Room> getRoomListByAccommodationId(long accommodationId) {
        List<Room> rooms = roomRepository.findByAccommodation_AccommodationId(accommodationId);

        for (Room room : rooms) {
            List<RoomImg> images = roomImgRepository.findByRoom_RoomId(room.getRoomId());
            room.setRoomImgs(images);
        }

        return rooms;
    }

    public Room getRoomById(long roomId) {
        Room room = roomRepository.findById(roomId).orElse(null);

        List<RoomImg> images = roomImgRepository.findByRoom_RoomId(roomId);
        room.setRoomImgs(images);

        return room;
    }

    public void editRoom(long roomId, String roomName, String roomDescription,
                         String roomPrice, String checkIn, String checkOut,
                         long roomMinCnt, long roomMaxCnt) {

        Room room = roomRepository.findById(roomId).orElse(null);

        if (room != null) {
            room.setRoomName(roomName);
            room.setRoomDescription(roomDescription);
            room.setRoomPrice(roomPrice);
            room.setCheckIn(checkIn);
            room.setCheckOut(checkOut);
            room.setRoomMinCnt(roomMinCnt);
            room.setRoomMaxCnt(roomMaxCnt);
        } else {
            throw new RuntimeException("룸ID " + roomId + "을 찾을 수 없습니다.");
        }


        roomRepository.save(room);
    }

    public long getAccommodationIdByRoomId(long roomId) {
        return roomRepository.findAccommodationIdByRoomId(roomId);
    }

    public void deleteRoomById(long roomId) {
        roomRepository.deleteById(roomId);
    }

    // 숙소 검색 기능
    public List<Room> getRoomsByAccommodationAndPeopleCnt(long accommodationId, Long peopleCnt) {
        Specification<Room> spec = RoomSpecification.findRoomForAccommodationAndPeopleCnt(accommodationId, peopleCnt);

        List<Room> rooms = roomRepository.findAll(spec);

        for (Room room : rooms) {
            List<RoomImg> images = roomImgRepository.findByRoom_RoomId(room.getRoomId());
            room.setRoomImgs(images);
        }

        return rooms;
    }
}
