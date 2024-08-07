package com.otl.accommodation.service;

import com.otl.accommodation.entity.Accommodation;
import com.otl.accommodation.entity.Room;
import com.otl.accommodation.repository.AccommodationRepository;
import com.otl.accommodation.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    private final AccommodationRepository accommodationRepository;

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> getRoomListByAccommodationId(long accommodationId) {
        return roomRepository.findByAccommodation_AccommodationId(accommodationId);
    }

    public Room getRoomById(long roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }

    public void editRoom(long roomId, String roomName, String roomDescription,
                         String roomPrice, String checkIn, String checkOut,
                         long roomMinCnt, long roomMaxCnt, String roomImageUrl) {

        Room room = roomRepository.findById(roomId).orElse(null);

        if (room != null) {
            room.setRoomName(roomName);
            room.setRoomDescription(roomDescription);
            room.setRoomPrice(roomPrice);
            room.setCheckIn(checkIn);
            room.setCheckOut(checkOut);
            room.setRoomMinCnt(roomMinCnt);
            room.setRoomMaxCnt(roomMaxCnt);
            room.setRoomImageUrl(roomImageUrl);
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
}
