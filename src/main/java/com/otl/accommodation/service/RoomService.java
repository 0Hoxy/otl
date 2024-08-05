package com.otl.accommodation.service;

import com.otl.accommodation.entity.Room;
import com.otl.accommodation.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> getRoomListByAccommodationId(long accommodationId) {
        return roomRepository.findByAccommodationId(accommodationId);
    }
}
