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

    // 숙소 카테고리 구분
    @GetMapping("/list/{themeName}")
    public String getAccommodationsByTheme(@PathVariable String themeName, Model model) {
        List<Accommodation> accommodations = accommodationService.getAccommodationsByThemeName(themeName);
        model.addAttribute("accommodations", accommodations);
        return "pages/accommodation/list";
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

    // 비지니스 부분

    // 사업자별 숙소 리스트 => 로그인(사업자 역할) 기능과 연결 후 수정 필요함
    @GetMapping("/business/list")
    public String showAccommodationBy(Model model) {
        List<Accommodation> accommodations = accommodationService.getAccommodations();
        model.addAttribute("accommodations", accommodations);
        return "pages/accommodation/business/list";
    }

    // 숙소 id 를 파라미터로 받기
    @GetMapping("/business/roomlist")
    public String showAccommodationRoomList(@RequestParam(name = "id") long accommodationId, Model model) {
        List<Room> rooms = roomService.getRoomListByAccommodationId(accommodationId);
        model.addAttribute("rooms", rooms);
        return "pages/accommodation/business/roomlist";
    }

    // 숙소 등록 페이지 show
    @GetMapping("/business/create")
    public String showCreateAccommodation(Model model) {
        model.addAttribute("accommodation", new Accommodation());
        return "pages/accommodation/business/create";
    }

    // 숙소 등록
    @PostMapping("/business/create")
    public String createAccommodation(@RequestParam("themeName") String themeName,
                                      @RequestParam("accommodationName") String accommodationName,
                                      @RequestParam("accommodationAddress") String accommodationAddress,
                                      @RequestParam("pictureUrl") String pictureUrl,
                                      Model model) {

        accommodationService.addAccommodation(themeName, accommodationName, accommodationAddress, pictureUrl);
        return "redirect:/accommodation/business/list";
    }

    // 숙소 수정 페이지 show
    @GetMapping("/business/update/{accommodationId}")
    public String showUpdateAccommodation(@PathVariable long accommodationId, Model model) {
        Accommodation accommodation = accommodationService.getAccommodation(accommodationId);
        model.addAttribute("accommodation", accommodation);
        return "pages/accommodation/business/update";
    }

    @PostMapping("/business/update/{accommodationId}")
    public String updateAccommodation(@PathVariable long accommodationId,
                                      @RequestParam("themeName") String themeName,
                                      @RequestParam("accommodationName") String accommodationName,
                                      @RequestParam("accommodationAddress") String accommodationAddress,
                                      @RequestParam("pictureUrl") String pictureUrl) {

        accommodationService.editAccommodation(accommodationId, themeName, accommodationName, accommodationAddress, pictureUrl);
        return "redirect:/accommodation/business/list";
    }

    @PostMapping("/business/delete/{accommodationId}")
    public String deleteAccommodation(@PathVariable long accommodationId) {
        accommodationService.deleteAccommodation(accommodationId);
        return "redirect:/accommodation/business/list";
    }


}
