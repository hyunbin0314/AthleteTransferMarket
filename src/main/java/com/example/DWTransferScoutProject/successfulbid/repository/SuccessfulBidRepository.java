package com.example.DWTransferScoutProject.successfulbid.repository;

import com.example.DWTransferScoutProject.auction.entity.Auction;
import com.example.DWTransferScoutProject.successfulbid.entity.SuccessfulBid;
import com.example.DWTransferScoutProject.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SuccessfulBidRepository extends CrudRepository<SuccessfulBid, Long> {
    Optional<SuccessfulBid> findByAuction(Auction auction);

    List<SuccessfulBid> findAllByUser(User user);


}
