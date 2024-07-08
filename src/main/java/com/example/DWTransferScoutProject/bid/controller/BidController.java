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



    // 로그인한 유저가 입찰을 하면 BidEntity에 경매글에 대한 입찰자 데이터를 생성후 UserDto를 리턴
    @PostMapping("/bid")
    public ResponseEntity<?> doBid(@AuthenticationPrincipal AccountDetailsImpl accountDetails, @RequestBody Auction auction) {
        Auction auction1 = auctionRepository.findById(auction.getId()).orElse(null); // 경매 정보 가져오기

        if(auction1.getTransferFee() > auction.getTransferFee()) {
            System.out.println("현재 : " + auction1.getTransferFee());
            System.out.println("입찰료 : " + auction.getTransferFee());
            return ResponseEntity.badRequest().body("현재 이적료보다 더 큰 금액을 써야합니다.");
        }


        // 입찰 정보 업데이트
        auction1.setTransferFee(auction.getTransferFee());
        auctionRepository.save(auction1);

        // 입찰 정보 저장
        Bid bid = new Bid();
        bid.setBidTime(LocalDateTime.now());
        bid.setUser((User) accountDetails.getAccount());
        bid.setTransferFee(auction.getTransferFee());
        bid.setAuction(auction);

        bidRepository.save(bid);

        // 사용자 DTO 생성 및 반환
        UserDto userDto = userService.mapToDTO(bid.getUser());
        BidDto bidDto = new BidDto();
        bidDto.setUserDto(userDto);

        return ResponseEntity.ok(bidDto.getUserDto());
    }



    // 경매글 클릭시 글에 대한 id값을 받고 입찰자의 userDto를 반환
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
