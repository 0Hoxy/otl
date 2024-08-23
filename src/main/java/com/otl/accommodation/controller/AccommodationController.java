package com.otl.accommodation.controller;

import com.otl.accommodation.dto.SearchRequestDTO;
import com.otl.accommodation.entity.Accommodation;
import com.otl.accommodation.entity.AccommodationImg;
import com.otl.accommodation.entity.Room;
import com.otl.accommodation.repository.AccommodationImgRepository;
import com.otl.accommodation.repository.AccommodationRepository;
import com.otl.accommodation.service.AccommodationImgService;
import com.otl.accommodation.service.AccommodationService;
import com.otl.accommodation.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/accommodation")
public class AccommodationController {

    private final AccommodationService accommodationService;
    private final RoomService roomService;
    private final AccommodationImgService accommodationImgService;
    private final AccommodationImgRepository accommodationImgRepository;
    private final AccommodationRepository accommodationRepository;

    // 전체 숙소 리스트
    @GetMapping("/list")
    public String showAccommodation(Model model) {
        List<Accommodation> accommodations = accommodationService.getAccommodations();
        model.addAttribute("accommodations", accommodations);
        return "pages/accommodation/list";
    }

    // 숙소 검색
    @GetMapping("/search")
    public String showAccommodationSearch(SearchRequestDTO search, Model model) {
        List<Accommodation> accommodations = accommodationService.searchAccommodation(search);
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
    public String showAccommodationDetail(@PathVariable long accommodationId,
                                          @RequestParam(value = "checkInDate", required = false) String checkInDate,
                                          @RequestParam(value = "checkOutDate", required = false) String checkOutDate,
                                          @RequestParam(value = "peopleCnt", required = false) Long peopleCnt,
                                          Model model) {
        Accommodation accommodation = accommodationService.getAccommodation(accommodationId);
        List<Room> rooms;

        if (peopleCnt != null) {
            rooms = roomService.getRoomsByAccommodationAndPeopleCnt(accommodationId, peopleCnt);
        } else {
            rooms = roomService.getRoomListByAccommodationId(accommodationId);
        }

        model.addAttribute("accommodation", accommodation);
        model.addAttribute("rooms", rooms);

        // 체크인, 체크아웃 날짜와 인원 수 모델에 추가
        model.addAttribute("checkInDate", checkInDate);
        model.addAttribute("checkOutDate", checkOutDate);
        model.addAttribute("peopleCnt", peopleCnt);
        return "pages/accommodation/detail";
    }

    // 비지니스 부분

    // 사업자별 숙소 리스트 => 로그인(사업자 역할) 기능과 연결 후 수정 필요함
    @GetMapping("/business/list")
    public String showAccommodationBy(Model model, @PageableDefault(size = 5) Pageable pageable) {
        Page<Accommodation> accommodations = accommodationService.getAdminAccommodations(pageable);
        model.addAttribute("accommodations", accommodations);
        return "pages/accommodation/business/list";
    }

    // 숙소 id 를 파라미터로 받기
    @GetMapping("/business/roomlist")
    public String showAccommodationRoomList(@RequestParam(name = "id") long accommodationId, @PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Room> rooms = roomService.getAdminRoomListByAccommodationId(accommodationId, pageable);
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
                                      @RequestParam("accommodationDescription") String accommodationDescription,
                                      @RequestParam("accommodationImgs") List<MultipartFile> accommodationImgs,
                                      Model model) throws IOException {

        Accommodation accommodation = accommodationService.addAccommodation(themeName, accommodationName, accommodationAddress, accommodationDescription);

        // 이미지 파일 저장
        for (MultipartFile file : accommodationImgs) {
            AccommodationImg accommodationImg = new AccommodationImg();
            accommodationImg.setAccommodation(accommodation);
            if (!file.isEmpty()) {
                accommodationImgService.saveAccommodationImg(accommodationImg, file);
            }
        }

        if (accommodationImgs.get(0).isEmpty()) {
            model.addAttribute("errorMessage", "숙소 이미지는 최소 한개는 넣어야합니다");
        }

        return "redirect:/accommodation/business/list";
    }

    // 숙소 수정 페이지 show
    @GetMapping("/business/update/{accommodationId}")
    public String showUpdateAccommodation(@PathVariable long accommodationId,
                                          Model model) {
        Accommodation accommodation = accommodationService.getAccommodation(accommodationId);
        List<AccommodationImg> accommodationImgs = accommodationImgRepository.findByAccommodation_AccommodationId(accommodationId);

        model.addAttribute("accommodation", accommodation);
        model.addAttribute("accommodationImgs", accommodationImgs);
        return "pages/accommodation/business/update";
    }

    @PostMapping("/business/update/{accommodationId}")
    public String updateAccommodation(@PathVariable long accommodationId,
                                      @RequestParam("themeName") String themeName,
                                      @RequestParam("accommodationName") String accommodationName,
                                      @RequestParam("accommodationAddress") String accommodationAddress,
                                      @RequestParam("accommodationDescription") String accommodationDescription,
                                      @RequestParam("accommodationImgs") List<MultipartFile> accommodationImgs,
                                      @RequestParam(value = "deleteImgIds", required = false) List<String> deleteImgIds) throws IOException {

        // 먼저 삭제할 이미지가 있다면 삭제 수행
        if (deleteImgIds != null && !deleteImgIds.isEmpty()) {

            List<Long> longDeleteImgIds = deleteImgIds.stream()
                    .map(Long::parseLong) // 각 String 값을 Long으로 변환
                    .collect(Collectors.toList());

            for (Long imgId : longDeleteImgIds) {
                AccommodationImg img = accommodationImgRepository.findById(imgId)
                        .orElseThrow(() -> new NoSuchElementException("이미지 ID가 잘못되었습니다: " + imgId));
                accommodationImgService.deleteAccommodationImg(img); // 이미지 삭제
            }
        }

        // 이미지 추가
        for (MultipartFile file : accommodationImgs) {
            if (!file.isEmpty()) {
                AccommodationImg accommodationImg = new AccommodationImg();
                accommodationImg.setAccommodation(accommodationRepository.findById(accommodationId).orElse(null));
                accommodationImgService.saveAccommodationImg(accommodationImg, file);
            }
        }

        accommodationService.editAccommodation(accommodationId, themeName, accommodationName, accommodationAddress, accommodationDescription);

        return "redirect:/accommodation/business/list";
    }

    @PostMapping("/business/delete/{accommodationId}")
    public String deleteAccommodation(@PathVariable long accommodationId) throws IOException {

        // 상품 이미지 조회
        List<AccommodationImg> accommodationImgs = accommodationImgRepository.findByAccommodation_AccommodationId(accommodationId);

        for (AccommodationImg img : accommodationImgs) {
            accommodationImgService.deleteAccommodationImg(img);
        }

        accommodationService.deleteAccommodation(accommodationId);

        return "redirect:/accommodation/business/list";
    }


}
