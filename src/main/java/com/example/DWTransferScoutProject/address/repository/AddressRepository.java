package com.example.DWTransferScoutProject.address.repository;

import com.example.DWTransferScoutProject.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
