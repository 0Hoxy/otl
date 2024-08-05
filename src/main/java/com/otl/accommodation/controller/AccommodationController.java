package com.otl.accommodation.controller;

import com.otl.accommodation.entity.Accommodation;
import com.otl.accommodation.entity.Room;
import com.otl.accommodation.service.AccommodationService;
import com.otl.accommodation.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/accommodation")
public class AccommodationController {

    private final AccommodationService accommodationService;
    private final RoomService roomService;

    // 전체 숙소 리스트
    @GetMapping("/list")
    public String showAccommodation(Model model) {
        List<Accommodation> accommodations = accommodationService.getAccommodations();
        model.addAttribute("accommodations", accommodations);
        return "pages/accommodation/list";
    }

    // 숙소 등록 페이지 show
    @GetMapping("/create")
    public String showCreateAccommodation(Model model) {
        model.addAttribute("accommodation", new Accommodation());
        return "pages/accommodation/create";
    }

    // 숙소 등록
    @PostMapping("/create")
    public String createAccommodation(@RequestParam("themeName") String themeName,
                                      @RequestParam("accommodationName") String accommodationName,
                                      @RequestParam("accommodationAddress") String accommodationAddress,
                                      @RequestParam("pictureUrl") String pictureUrl,
                                      Model model) {

        accommodationService.createAccommodation(themeName, accommodationName, accommodationAddress, pictureUrl);
        return "redirect:/accommodation/list";
    }

    // 숙소 상세페이지
    @GetMapping("/detail/{accommodationId}")
    public String showAccommodationDetail(@PathVariable long accommodationId, Model model) {
        Accommodation accommodation = accommodationService.getAccommodation(accommodationId);
        List<Room> rooms = roomService.getRoomListByAccommodationId(accommodationId);
        model.addAttribute("accommodation", accommodation);
        model.addAttribute("rooms", rooms);
        return "pages/accommodation/detail";
    }

    // 숙소 카테고리 구분
    @GetMapping("/list/{themeName}")
    public String getAccommodationsByTheme(@PathVariable String themeName, Model model) {
        List<Accommodation> accommodations = accommodationService.getAccommodationsByThemeName(themeName);
        model.addAttribute("accommodations", accommodations);
        return "pages/accommodation/list";
    }


//    // 분류코드별 숙소 리스트
//    @GetMapping("/list/{themeCode}")
//    public String showAccommodationThemeList(@PathVariable("themeCode") long themeCode, Model model) {
//        List<AccommodationDTO> accommodations = accommodationService.getAccommodationsByThemeCode(themeCode);
//        model.addAttribute("accommodations", accommodations);
//        return "pages/accommodation/list";
//    }
//
//    // 숙소 상세 페이지
//    @GetMapping("/detail/{accommodationName}")
//    public String showAccommodationDetail(@PathVariable("accommodationName") String accommodationName, Model model) {
//        AccommodationDTO accommodation = accommodationService.getAccommodationByName(accommodationName);
//        model.addAttribute("accommodation", accommodation);
//        return "pages/accommodation/detail";
//    }
//
//    // 숙소 검색
//    @GetMapping("/search")
//    public String showAccommodationSearch(@RequestParam(name = "searchContent", required = false) String searchContent, Model model) {
//        if (searchContent != null && !searchContent.trim().isEmpty()) {
//            List<AccommodationDTO> accommodations = accommodationService.searchAccommodationByName(searchContent);
//            model.addAttribute("searchContent", searchContent);
//            model.addAttribute("accommodations", accommodations);
//        }
//
//        return "pages/accommodation/list";
//    }

}
