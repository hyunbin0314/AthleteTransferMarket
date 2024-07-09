package com.example.DWTransferScoutProject.address.dto;

import com.example.DWTransferScoutProject.address.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    private String street;     // 도로명 주소
    private String city;       // 시, 군, 구
    private String zipCode;    // 우편번호
    private String country;    // 국가

    public AddressDto(Address address) {
        this.street = address.getStreet();
        this.city = address.getCity();
        this.zipCode = address.getZipCode();
        this.country = address.getCountry();
    }
}

