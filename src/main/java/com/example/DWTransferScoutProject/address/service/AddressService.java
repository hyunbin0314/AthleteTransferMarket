package com.example.DWTransferScoutProject.address.service;

import com.example.DWTransferScoutProject.address.dto.AddressDto;
import com.example.DWTransferScoutProject.address.entity.Address;
import com.example.DWTransferScoutProject.address.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address saveAddress(AddressDto addressDto) {
        Address address = Address.builder()
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .zipCode(addressDto.getZipCode())
                .country(addressDto.getCountry())
                .build();
        return addressRepository.save(address);
    }

    public Address updateAddress(Long id, AddressDto addressDto) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));
        address.updateAddress(addressDto);
        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
