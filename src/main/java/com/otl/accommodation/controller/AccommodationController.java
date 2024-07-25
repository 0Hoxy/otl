package com.otl.accommodation.controller;

import com.otl.accommodation.dto.AccommodationDTO;
import com.otl.accommodation.service.AccommodationService;
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

    // 전체 숙소 리스트
    @GetMapping("/list")
    public String showAccommodation(Model model) {
        List<AccommodationDTO> accommodations = accommodationService.getAccommodations();
        model.addAttribute("accommodations", accommodations);
        return "pages/accommodation/list";
    }

    // 분류코드별 숙소 리스트
    @GetMapping("/list/{themeCode}")
    public String showAccommodationThemeList(@PathVariable("themeCode") long themeCode, Model model) {
        List<AccommodationDTO> accommodations = accommodationService.getAccommodationsByThemeCode(themeCode);
        model.addAttribute("accommodations", accommodations);
        return "pages/accommodation/list";
    }

    // 숙소 상세 페이지
    @GetMapping("/detail/{accommodationName}")
    public String showAccommodationDetail(@PathVariable("accommodationName") String accommodationName, Model model) {
        AccommodationDTO accommodation = accommodationService.getAccommodationByName(accommodationName);
        model.addAttribute("accommodation", accommodation);
        return "pages/accommodation/detail";
    }

    // 숙소 검색
    @GetMapping("/search")
    public String showAccommodationSearch(@RequestParam(name = "searchContent", required = false) String searchContent, Model model) {
        if (searchContent != null && !searchContent.trim().isEmpty()) {
            List<AccommodationDTO> accommodations = accommodationService.searchAccommodationByName(searchContent);
            model.addAttribute("searchContent", searchContent);
            model.addAttribute("accommodations", accommodations);
        }

        return "pages/accommodation/list";
    }

}
