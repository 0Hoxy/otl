package com.otl.accommodation.controller;

import com.otl.accommodation.dto.HotelResponseDTO;
import com.otl.accommodation.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hotel")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/list")
    public String hotelList(Model model) {
        return "pages/hotel/list";
    }

    @GetMapping("/search")
    public String searchHotels(@RequestParam String searchContent,
                               @RequestParam String checkInDate,
                               @RequestParam String checkOutDate,
                               @RequestParam Long adults, Model model) {
        List<HotelResponseDTO> hotelResponseDTOS =
                hotelService.searchHotels(searchContent, checkInDate, checkOutDate, adults);
        model.addAttribute("hotelResponseDTOS", hotelResponseDTOS);
        return "pages/hotel/list";
    }
}
