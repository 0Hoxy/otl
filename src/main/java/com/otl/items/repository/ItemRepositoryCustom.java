package com.otl.items.repository;

import com.otl.items.dto.ItemSearchDto;
import com.otl.items.entity.Item;
import com.otl.main.dto.MainItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
