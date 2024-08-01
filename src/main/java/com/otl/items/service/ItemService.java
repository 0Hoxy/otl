package com.otl.items.service;

import com.otl.items.dto.ItemFormDto;
import com.otl.items.entity.Item;
import com.otl.items.entity.ItemImg;
import com.otl.items.repository.ItemImgRepository;
import com.otl.items.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        //상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            //else 중괄호 안에 이미지 저장 메소드가 들어가 있어서 i == 0 일때는 상품 이미지가 저장되지 않는 상황이 발생, 전체가 해당 되도록 수정했음
            if (i == 0) { //첫번째 이미지일 경우 대표 상품 이미지 여부 값을 "Y"로 세팅한다.
                itemImg.setRepimgYn("Y");
            } else {
                itemImg.setRepimgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i)); //상품의 이미지 정보를 저장한다.
        }
        return item.getId();
    }
}
