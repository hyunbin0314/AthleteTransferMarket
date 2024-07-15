package com.example.DWTransferScoutProject.successfulbid.controller;

import com.example.DWTransferScoutProject.auction.dto.AuctionDto;
import com.example.DWTransferScoutProject.auction.entity.Auction;
import com.example.DWTransferScoutProject.auction.repository.AuctionRepository;
import com.example.DWTransferScoutProject.auction.service.AuctionService;
import com.example.DWTransferScoutProject.auth.security.AccountDetailsImpl;
import com.example.DWTransferScoutProject.bid.dto.BidDto;
import com.example.DWTransferScoutProject.bid.entity.Bid;
import com.example.DWTransferScoutProject.bid.repository.BidRepository;
import com.example.DWTransferScoutProject.successfulbid.dto.SuccessfulBidDto;
import com.example.DWTransferScoutProject.successfulbid.entity.SuccessfulBid;
import com.example.DWTransferScoutProject.successfulbid.repository.SuccessfulBidRepository;
import com.example.DWTransferScoutProject.successfulbid.service.SuccessfulBidService;
import com.example.DWTransferScoutProject.user.entity.User;
import com.example.DWTransferScoutProject.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private SuccessfulBidService successfulBidService;


    // 낙찰 DB 저장
    @PostMapping("/successfulbid")
    public ResponseEntity<?> createSuccessfulBid(@RequestBody SuccessfulBidDto successfulBidDto) {

        if(successfulBidDto.getBidUserId() == null) {
            return null;
        }



        Optional<Bid> bidOptional = bidRepository.findById(successfulBidDto.getBidUserId());
        Optional<Auction> auctionOptional = auctionRepository.findById(successfulBidDto.getAuctionId());

        if(successfulBidRepository.findByAuction(auctionOptional.get()).isPresent()) return null;


        if (bidOptional.isPresent() && auctionOptional.isPresent()) {
            SuccessfulBid successfulBid = successfulBidService.createSuccessfulBidDto(successfulBidDto); // 낙찰 DB 생성 및 저장
            successfulBidService.toDto(successfulBid); // Dto 변환

            return ResponseEntity.ok(successfulBid);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bid or Auction not found");
        }
    }


    //로그인한 유저의 낙찰 DB 리턴
    @GetMapping("/successfulbid")
    public ResponseEntity<List<SuccessfulBidDto>> successfulBid(@AuthenticationPrincipal AccountDetailsImpl accountDetails) {
        List<SuccessfulBid> successfulBidList = successfulBidRepository.findAllByUser((User) accountDetails.getAccount());

        List<SuccessfulBidDto> successfulBidDtos = successfulBidService.toDtoList(successfulBidList);


        return ResponseEntity.ok(successfulBidDtos);
    }

    //모든 낙찰 DB 리턴
    @GetMapping("/allsuccessfulbid")
    public ResponseEntity<List<SuccessfulBidDto>> allSuccessfulBid() {
        List<SuccessfulBid> successfulBidList = (List<SuccessfulBid>) successfulBidRepository.findAll();

        List<SuccessfulBidDto> successfulBidDtos = successfulBidService.toDtoList(successfulBidList);


        return ResponseEntity.ok(successfulBidDtos);
    }


}
