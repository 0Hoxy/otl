package com.otl.accommodation.controller;

import com.otl.accommodation.entity.Room;
import com.otl.accommodation.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/create/{accommodationId}")
    public String showCreateRoom(@PathVariable("accommodationId") long accommodationId, Model model) {
        Room room = new Room();
        room.setAccommodationId(accommodationId);
        model.addAttribute("room", room);
        return "pages/accommodation/roomCreate";
    }

    @PostMapping("/create/{accommodationId}")
    public String createRoom(@PathVariable("accommodationId") long accommodationId,
                             @ModelAttribute Room room) {
        room.setAccommodationId(accommodationId);
        roomService.createRoom(room);
        return "redirect:/accommodation/detail/" + accommodationId; // 숙소 상세 페이지로 리다이렉트
    }
}
