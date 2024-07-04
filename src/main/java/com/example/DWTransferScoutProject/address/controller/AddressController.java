package com.example.DWTransferScoutProject.address.controller;

import com.example.DWTransferScoutProject.address.dto.AddressDto;
import com.example.DWTransferScoutProject.address.entity.Address;
import com.example.DWTransferScoutProject.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody AddressDto addressDto) {
        Address address = addressService.saveAddress(addressDto);
        return ResponseEntity.ok(address);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody AddressDto addressDto) {
        Address address = addressService.updateAddress(id, addressDto);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
