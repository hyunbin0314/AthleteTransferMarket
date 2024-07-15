package com.example.DWTransferScoutProject.successfulbid.service;

import com.example.DWTransferScoutProject.auction.entity.Auction;
import com.example.DWTransferScoutProject.auction.repository.AuctionRepository;
import com.example.DWTransferScoutProject.auction.service.AuctionService;
import com.example.DWTransferScoutProject.bid.entity.Bid;
import com.example.DWTransferScoutProject.bid.repository.BidRepository;
import com.example.DWTransferScoutProject.successfulbid.dto.SuccessfulBidDto;
import com.example.DWTransferScoutProject.successfulbid.entity.SuccessfulBid;
import com.example.DWTransferScoutProject.successfulbid.repository.SuccessfulBidRepository;
import com.example.DWTransferScoutProject.user.entity.User;
import com.example.DWTransferScoutProject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SuccessfulBidService {

    @Autowired
    private AuctionService auctionService;
    @Autowired
    private UserService userService;
    @Autowired
    private SuccessfulBidRepository successfulBidRepository;
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private AuctionRepository auctionRepository;


    public SuccessfulBid createSuccessfulBidDto(SuccessfulBidDto successfulBidDto) {
        Optional<Bid> bidOptional = bidRepository.findById(successfulBidDto.getBidUserId());
        Optional<Auction> auctionOptional = auctionRepository.findById(successfulBidDto.getAuctionId());

        SuccessfulBid successfulBid = new SuccessfulBid();
        successfulBid.setAuction(auctionOptional.get());
        successfulBid.setUser(bidOptional.get().getUser());

        successfulBidRepository.save(successfulBid);

        return successfulBid;
    }

    public SuccessfulBidDto toDto(SuccessfulBid successfulBid) {


        SuccessfulBidDto successfulBidDto = new SuccessfulBidDto();

        successfulBidDto.setAuctionDto(auctionService.toDto(successfulBid.getAuction()));
        successfulBidDto.setId(successfulBid.getId());
        successfulBidDto.setUserDto(userService.mapToDTO(successfulBid.getUser()));

        return successfulBidDto;
    }

    public List<SuccessfulBidDto> toDtoList(List<SuccessfulBid> successfulBidList) {
        List<SuccessfulBidDto> successfulBidDtos = new ArrayList<>();
        for (SuccessfulBid successfulBid : successfulBidList) {
            SuccessfulBidDto successfulBidDto = toDto(successfulBid);

            successfulBidDtos.add(successfulBidDto);
        } return successfulBidDtos;
    }
}
