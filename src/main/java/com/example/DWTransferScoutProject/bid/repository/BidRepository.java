package com.example.DWTransferScoutProject.bid.repository;

import com.example.DWTransferScoutProject.auction.entity.Auction;
import com.example.DWTransferScoutProject.bid.entity.Bid;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BidRepository extends CrudRepository<Bid, Long> {
    Optional<Bid> findTopByAuctionOrderByBidTimeDesc(Auction auction);
}
