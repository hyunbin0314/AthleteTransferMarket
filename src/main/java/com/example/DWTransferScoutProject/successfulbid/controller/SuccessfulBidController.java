package com.example.DWTransferScoutProject.successfulbid.controller;

import com.example.DWTransferScoutProject.auction.dto.AuctionDto;
import com.example.DWTransferScoutProject.auction.entity.Auction;
import com.example.DWTransferScoutProject.auction.repository.AuctionRepository;
import com.example.DWTransferScoutProject.bid.dto.BidDto;
import com.example.DWTransferScoutProject.bid.entity.Bid;
import com.example.DWTransferScoutProject.bid.repository.BidRepository;
import com.example.DWTransferScoutProject.successfulbid.dto.SuccessfulBidDto;
import com.example.DWTransferScoutProject.successfulbid.entity.SuccessfulBid;
import com.example.DWTransferScoutProject.successfulbid.repository.SuccessfulBidRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api")
public class SuccessfulBidController {

    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private SuccessfulBidRepository successfulBidRepository;


    //
    @PostMapping("/successfulbid")
    public ResponseEntity<?> createSuccessfulBid(@RequestBody SuccessfulBidDto requestDto) {

        System.out.println("받은 낙잘자 id" + requestDto.getBidUserId());
        System.out.println("받은 경매글 id" + requestDto.getAuctionId());

        if(requestDto.getBidUserId() == null) {
            return null;
        }



        Optional<Bid> bidOptional = bidRepository.findById(requestDto.getBidUserId());
        Optional<Auction> auctionOptional = auctionRepository.findById(requestDto.getAuctionId());

        if(successfulBidRepository.findByAuction(auctionOptional.get()).isPresent()) return null;


        if (bidOptional.isPresent() && auctionOptional.isPresent()) {
            Bid bid = bidOptional.get();
            Auction auction = auctionOptional.get();

            SuccessfulBid successfulBid = new SuccessfulBid();
            successfulBid.setAuction(auction);
            successfulBid.setUser(bid.getUser());

            successfulBidRepository.save(successfulBid);

            return ResponseEntity.ok("Successful bid created");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bid or Auction not found");
        }
    }

}
