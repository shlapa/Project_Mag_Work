package com.amr.project.mapper;

import com.amr.project.converter.ImageToImageDtoConverter;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.Shop;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ShopMapper {

    ShopDto shopToShopDto(Shop shop);


    Shop createShopDtoToShop(ShopDto shopDto);


    Shop shopDtoToShop(ShopDto shopDto);

    default ImageDto imageMap(Image image){
        return ImageToImageDtoConverter.convertImageToImageDto(image);
    }

    abstract public List<ShopDto> toShopDtos(List<Shop> shops);

}
