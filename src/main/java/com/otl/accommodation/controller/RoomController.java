package com.otl.accommodation.controller;

import com.otl.accommodation.entity.Accommodation;
import com.otl.accommodation.entity.Room;
import com.otl.accommodation.repository.AccommodationRepository;
import com.otl.accommodation.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/accommodation/business/room")
public class RoomController {

    private final RoomService roomService;
    private final AccommodationRepository accommodationRepository;

    @GetMapping("/create/{accommodationId}")
    public String showCreateRoom(@PathVariable("accommodationId") long accommodationId, Model model) {
        Room room = new Room();
        model.addAttribute("room", room);
        model.addAttribute("accommodationId", accommodationId);
        return "pages/accommodation/business/roomcreate";
    }

    @PostMapping("/create/{accommodationId}")
    public String createRoom(@PathVariable("accommodationId") long accommodationId,
                             @ModelAttribute Room room) {

        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElse(null);
        room.setAccommodation(accommodation);
        roomService.addRoom(room);
        return "redirect:/accommodation/business/roomlist?id=" + accommodationId; // 숙소 상세 페이지로 리다이렉트
    }

    @GetMapping("/update/{roomId}")
    public String showUpdateRoom(@PathVariable("roomId") long roomId, Model model) {
        Room room = roomService.getRoomById(roomId);
        model.addAttribute("room", room);
        return "pages/accommodation/business/roomupdate";
    }

    @PostMapping("/update/{roomId}")
    public String UpdateRoom(@PathVariable("roomId") long roomId,
                             @RequestParam("roomName") String roomName,
                             @RequestParam("roomDescription") String roomDescription,
                             @RequestParam("roomPrice") String roomPrice,
                             @RequestParam("checkIn") String checkIn,
                             @RequestParam("checkOut") String checkOut,
                             @RequestParam("roomMinCnt") long roomMinCnt,
                             @RequestParam("roomMaxCnt") long roomMaxCnt,
                             @RequestParam("roomImageUrl") String roomImageUrl) {

        long accommodationId = roomService.getAccommodationIdByRoomId(roomId);
        roomService.editRoom(roomId, roomName, roomDescription, roomPrice, checkIn, checkOut, roomMinCnt, roomMaxCnt, roomImageUrl);

        return "redirect:/accommodation/business/roomlist?id=" + accommodationId;
    }

    @PostMapping("/delete/{roomId}")
    public String deleteRoom(@PathVariable("roomId") long roomId) {
        long accommodationId = roomService.getAccommodationIdByRoomId(roomId);
        roomService.deleteRoomById(roomId);
        return "redirect:/accommodation/business/roomlist?id=" + accommodationId;
    }
}
