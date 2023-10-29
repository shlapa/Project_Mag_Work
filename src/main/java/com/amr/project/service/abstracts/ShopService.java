package com.amr.project.service.abstracts;

import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShopService extends ReadWriteService<Shop, Long> {
    List<ShopDto> getShopByName(String name);
    Shop getShopById(Long shopId);
    ShopDto getShopDtoById(Long shopId);


    List<Shop> findAll();

    Shop findById(Long id);

    void deleteById(Long id);

    // void persist(Shop shop);

    void update(Shop shop);

    // void deleteByIdEnable(Long id);

    Page<ShopDto> findAll(Pageable pageable);

}
