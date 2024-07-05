package com.example.DWTransferScoutProject.address.entity;

import com.example.DWTransferScoutProject.address.dto.AddressDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;     // 도로명 주소
    private String city;       // 시, 군, 구
    private String zipCode;    // 우편번호
    private String country;    // 국가

    public void updateAddress(AddressDto addressDto) {
        this.street = addressDto.getStreet();
        this.city = addressDto.getCity();
        this.zipCode = addressDto.getZipCode();
        this.country = addressDto.getCountry();
    }
}
