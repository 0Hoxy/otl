package com.otl.accommodation.controller;

import com.otl.accommodation.entity.Accommodation;
import com.otl.accommodation.entity.AccommodationImg;
import com.otl.accommodation.entity.Room;
import com.otl.accommodation.entity.RoomImg;
import com.otl.accommodation.repository.AccommodationRepository;
import com.otl.accommodation.repository.RoomImgRepository;
import com.otl.accommodation.repository.RoomRepository;
import com.otl.accommodation.service.RoomImgService;
import com.otl.accommodation.service.RoomService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/accommodation/business/room")
public class RoomController {

    private final RoomService roomService;
    private final RoomImgService roomImgService;
    private final AccommodationRepository accommodationRepository;
    private final RoomImgRepository roomImgRepository;
    private final RoomRepository roomRepository;

    @GetMapping("/create/{accommodationId}")
    public String showCreateRoom(@PathVariable("accommodationId") long accommodationId,
                                 Model model) {
        model.addAttribute("room", new Room());
        model.addAttribute("accommodationId", accommodationId);
        return "pages/accommodation/business/roomcreate";
    }

    @PostMapping("/create/{accommodationId}")
    public String createRoom(@PathVariable("accommodationId") long accommodationId,
                             @RequestParam("roomName") String roomName,
                             @RequestParam("roomDescription") String roomDescription,
                             @RequestParam("roomPrice") String roomPrice,
                             @RequestParam("checkIn") String checkIn,
                             @RequestParam("checkOut") String checkOut,
                             @RequestParam("roomMinCnt") long roomMinCnt,
                             @RequestParam("roomMaxCnt") long roomMaxCnt,
                             @RequestParam("roomImgs") List<MultipartFile> roomImgs) throws IOException {

        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElse(null);
        Room room = roomService.addRoom(accommodation, roomName, roomDescription, roomPrice, checkIn, checkOut, roomMinCnt, roomMaxCnt);

        // 이미지 파일 저장
        for (MultipartFile file : roomImgs) {
            RoomImg roomImg = new RoomImg();
            roomImg.setRoom(room);
            if (!file.isEmpty()) {
                roomImgService.saveRoomImg(roomImg, file);
            }
        }

        return "redirect:/accommodation/business/roomlist?id=" + accommodationId; // 숙소 상세 페이지로 리다이렉트
    }

    @GetMapping("/update/{roomId}")
    public String showUpdateRoom(@PathVariable("roomId") long roomId, Model model) {
        Room room = roomService.getRoomById(roomId);
        List<RoomImg> roomImgs = roomImgRepository.findByRoom_RoomId(roomId);

        model.addAttribute("room", room);
        model.addAttribute("roomImgs", roomImgs);
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
                             @RequestParam("roomImgs") List<MultipartFile> roomImgs,
                             @RequestParam(value = "deleteImgIds", required = false) List<String> deleteImgIds) throws IOException {

        long accommodationId = roomService.getAccommodationIdByRoomId(roomId);

        // 먼저 삭제할 이미지가 있다면 삭제 수행
        if (deleteImgIds != null && !deleteImgIds.isEmpty()) {
            List<Long> longDeleteImgIds = deleteImgIds.stream()
                    .map(Long::parseLong) // 각 String 값을 Long으로 변환
                    .collect(Collectors.toList());

            for (Long imgId : longDeleteImgIds) {
                RoomImg img = roomImgRepository.findById(imgId)
                        .orElseThrow(() -> new NoSuchElementException("이미지 ID가 잘못되었습니다: " + imgId));
               roomImgService.deleteRoomImg(img); // 이미지 삭제
            }
        }

        // 이미지 추가
        for (MultipartFile file : roomImgs) {
            if (!file.isEmpty()) {
                RoomImg roomImg = new RoomImg();
                roomImg.setRoom(roomRepository.findById(roomId).orElse(null));
                roomImgService.saveRoomImg(roomImg, file);
            }
        }

        roomService.editRoom(roomId, roomName, roomDescription, roomPrice, checkIn, checkOut, roomMinCnt, roomMaxCnt);

        return "redirect:/accommodation/business/roomlist?id=" + accommodationId;
    }

    @PostMapping("/delete/{roomId}")
    public String deleteRoom(@PathVariable("roomId") long roomId) throws IOException {
        long accommodationId = roomService.getAccommodationIdByRoomId(roomId);

        // 상품 이미지 조회
        List<RoomImg> roomImgs = roomImgRepository.findByRoom_RoomId(roomId);

        for (RoomImg img : roomImgs) {
            roomImgService.deleteRoomImg(img);
        }

        roomService.deleteRoomById(roomId);
        return "redirect:/accommodation/business/roomlist?id=" + accommodationId;
    }
}
