package com.amr.project.service.testproject;

import com.amr.project.mapper.AddressMapper;
import com.amr.project.model.dto.AddressDto;
import com.amr.project.model.entity.Address;
import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Country;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressMapperTest {
    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);
    private Address address = new Address();
    private AddressDto addressDto = new AddressDto();

    {

        Country country = Country.builder()
                .id(1L)
                .name("qwertyqwerty")
                .build();
        City city = City.builder()
                .id(1L)
                .name("qwerty")
                .country(country)
                .build();
        address.setHouse("1");
        address.setAdditionalInfo("additionalInfo");
        address.setStreet("qwerty");
        address.setCity(city);
        address.setCityIndex("123456");

        addressDto.setCityIndex("123456");
        addressDto.setStreet("qwerty");
        addressDto.setHouse("1");
        addressDto.setCityId(1L);
        addressDto.setCity("qwerty");
        addressDto.setCountry("qwertyqwerty");
        addressDto.setCountryId(1L);
        addressDto.setAdditionalInfo("additionalInfo");
    }

    @Test
    void fieldsAddressDtoShouldBeEqualsFeildsAddress() {
        AddressDto addressDto1 = addressMapper.addressToAddressDto(address);
        assertEquals(address.getId(), addressDto1.getId());
        assertEquals(address.getCityIndex(), addressDto1.getCityIndex());
        assertEquals(address.getStreet(), addressDto1.getStreet());
        assertEquals(address.getHouse(), addressDto1.getHouse());
        assertEquals(address.getCity().getId(), addressDto1.getCityId());
        assertEquals(address.getId(), addressDto1.getId());
        assertEquals(address.getCity().getName(), addressDto1.getCity());
        assertEquals(address.getCity().getCountry().getName(), addressDto1.getCountry());
        assertEquals(address.getAdditionalInfo(), addressDto1.getAdditionalInfo());
    }

    @Test
    void fieldsAddressShouldBeEqualsFieldsAddressDto() {

        Address address1 = addressMapper.addressDtoToAddress(addressDto);
        assertEquals(addressDto.getId(), address1.getId());
        assertEquals(addressDto.getCityIndex(), address1.getCityIndex());
        assertEquals(addressDto.getStreet(), address1.getStreet());
        assertEquals(addressDto.getHouse(), address1.getHouse());
        assertEquals(addressDto.getCityId(), address1.getCity().getId());
        assertEquals(addressDto.getCity(), address1.getCity().getName());
        assertEquals(addressDto.getCountry(), address1.getCity().getCountry().getName());
        assertEquals(addressDto.getCountryId(), address1.getCity().getCountry().getId());
        assertEquals(addressDto.getAdditionalInfo(), address1.getAdditionalInfo());

    }
}
