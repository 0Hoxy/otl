package com.otl.accommodation.service;

import com.otl.accommodation.entity.AccommodationImg;
import com.otl.accommodation.repository.AccommodationImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationImgService {

    private final AccommodationImgRepository accommodationImgRepository;

    private final ImgFileService imgFileService;

    @Value("${accommodationImgLocation}")
    private String accommodationImgLocation;

    // 숙소 이미지 저장
    public void saveAccommodationImg(AccommodationImg accommodationImg, MultipartFile file) throws IOException {
        String originImgName = file.getOriginalFilename();
        String imgName = "";
        String imgPath = "";

        if (!StringUtils.isEmpty(originImgName)) {
            File directory = new File(accommodationImgLocation);
            if (!directory.exists()) {
                directory.mkdirs();  // 디렉토리가 존재하지 않으면 생성
            }

            imgName = imgFileService.uploadImgFile(accommodationImgLocation, originImgName, file.getBytes());
            imgPath = "/images/accommodation/" + imgName;
        }

        accommodationImg.accommodationImgUpload(originImgName, imgName, imgPath);
        accommodationImgRepository.save(accommodationImg);
    }

    public void deleteAccommodationImg(AccommodationImg accommodationImg) throws IOException {
        if (StringUtils.hasText(accommodationImg.getAccommodationImgName())) {
            imgFileService.deleteImgFile(accommodationImgLocation + "/" + accommodationImg.getOriaccommodationImgName());
        }

        accommodationImgRepository.delete(accommodationImg);
    }

}
