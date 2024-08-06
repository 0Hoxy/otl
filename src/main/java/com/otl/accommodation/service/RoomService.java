package com.otl.accommodation.service;

import com.otl.accommodation.entity.Room;
import com.otl.accommodation.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> getRoomListByAccommodationId(long accommodationId) {
        return roomRepository.findByAccommodationId(accommodationId);
    }

    public Room getRoomById(long roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }

    public void editRoom(long roomId, String roomName, String roomDescription,
                         String roomPrice, String checkIn, String checkOut,
                         long roomMinCnt, long roomMaxCnt, String roomImageUrl, long accommodationId) {

        Room room = roomRepository.findById(roomId).orElse(null);

        room.setRoomName(roomName);
        room.setRoomDescription(roomDescription);
        room.setRoomPrice(roomPrice);
        room.setCheckIn(checkIn);
        room.setCheckOut(checkOut);
        room.setRoomMinCnt(roomMinCnt);
        room.setRoomMaxCnt(roomMaxCnt);
        room.setRoomImageUrl(roomImageUrl);
        room.setAccommodationId(accommodationId);

        roomRepository.save(room);
    }

    public long getAccommodationIdByRoomId(long roomId) {
        return roomRepository.findAccommodationIdByRoomId(roomId);
    }

    public void deleteRoomById(long roomId) {
        roomRepository.deleteById(roomId);
    }
}
