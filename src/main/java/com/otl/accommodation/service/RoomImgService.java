package com.otl.accommodation.service;

import com.otl.accommodation.entity.AccommodationImg;
import com.otl.accommodation.entity.RoomImg;
import com.otl.accommodation.repository.RoomImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RoomImgService {

    private final RoomImgRepository roomImgRepository;

    private final ImgFileService imgFileService;

    @Value("${roomImgLocation}")
    private String roomImgLocation;

    // 숙소 이미지 저장
    public void saveRoomImg(RoomImg roomImg, MultipartFile file) throws IOException {
        String originImgName = file.getOriginalFilename();
        String imgName = "";
        String imgPath = "";

        if (!StringUtils.isEmpty(originImgName)) {
            File directory = new File(roomImgLocation);
            if (!directory.exists()) {
                directory.mkdirs();  // 디렉토리가 존재하지 않으면 생성
            }

            imgName = imgFileService.uploadImgFile(roomImgLocation, originImgName, file.getBytes());
            imgPath = "/images/room/" + imgName;
        }

        roomImg.roomImgUpload(originImgName, imgName, imgPath);
        roomImgRepository.save(roomImg);
    }

    public void deleteRoomImg(RoomImg roomImg) throws IOException {
        if (StringUtils.hasText(roomImg.getRoomImgName())) {
            imgFileService.deleteImgFile(roomImgLocation + "/" + roomImg.getOriroomImgName());
        }

        roomImgRepository.delete(roomImg);
    }
}
