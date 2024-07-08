package com.example.DWTransferScoutProject.successfulbid.repository;

import com.example.DWTransferScoutProject.auction.entity.Auction;
import com.example.DWTransferScoutProject.successfulbid.entity.SuccessfulBid;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SuccessfulBidRepository extends CrudRepository<SuccessfulBid, Long> {
    Optional<SuccessfulBid> findByAuction(Auction auction);
}
