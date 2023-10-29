package com.amr.project.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Long.class)
public class FavoriteDto {
    private Long id;
    private List<ItemDto> items;
    private List<ShopDto> shops;
    private Long userId;
    public int findShopById(Long shopId) {
        for (ShopDto shop : this.getShops()) {
            if (shop.getId().equals(shopId)) return this.getShops().indexOf(shop);
        }
        return -1;
    }
    public int findItemById(Long itemId) {
        for (ItemDto item : this.getItems()) {
            if (item.getId().equals(itemId)) return this.getItems().indexOf(item);
        }
        return -1;
    }
}