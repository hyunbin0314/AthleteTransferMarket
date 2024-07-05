package com.example.DWTransferScoutProject.bid.controller;

import com.example.DWTransferScoutProject.auction.entity.Auction;
import com.example.DWTransferScoutProject.auction.repository.AuctionRepository;
import com.example.DWTransferScoutProject.auth.security.AccountDetailsImpl;
import com.example.DWTransferScoutProject.bid.dto.BidDto;
import com.example.DWTransferScoutProject.bid.entity.Bid;
import com.example.DWTransferScoutProject.bid.repository.BidRepository;
import com.example.DWTransferScoutProject.user.dto.UserDto;
import com.example.DWTransferScoutProject.user.entity.User;
import com.example.DWTransferScoutProject.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api")
public class BidController {
    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserService userService;



    @PostMapping("/bid")
    public ResponseEntity<?> doBid(@AuthenticationPrincipal AccountDetailsImpl accountDetails, @RequestBody Auction auction) {

        Auction auction1 = auctionRepository.findById(auction.getId()).get();
        auction1.setTransferFee(auction.getTransferFee());

        auctionRepository.save(auction1);

        Bid bid = new Bid();
        bid.setBidTime(LocalDateTime.now());
        bid.setUser((User) accountDetails.getAccount());
        bid.setTransferFee(auction.getTransferFee());
        bid.setAuction(auction);

        bidRepository.save(bid);

        UserDto userDto = userService.mapToDTO(bid.getUser());


        BidDto bidDto = new BidDto();
        bidDto.setUserDto(userDto);

        return ResponseEntity.ok(bidDto.getUserDto());

    }

    @GetMapping("biduser")
    public ResponseEntity<?> bidUser(@RequestParam Long id) {
        System.out.println("받은 id: " + id);
        Auction auction = auctionRepository.findById(id).get();
        Optional<Bid> bid = bidRepository.findTopByAuctionOrderByBidTimeDesc(auction);

        if(!bid.isPresent()) return ResponseEntity.ok("");

        else {
            UserDto userDto = userService.mapToDTO(bid.get().getUser());
            return ResponseEntity.ok(userDto);
        }


    }

}
