package com.example.DWTransferScoutProject.auction.controller;

import com.example.DWTransferScoutProject.auction.dto.AuctionDto;
import com.example.DWTransferScoutProject.auction.entity.Auction;
import com.example.DWTransferScoutProject.auction.repository.AuctionRepository;
import com.example.DWTransferScoutProject.auction.service.AuctionService;
import com.example.DWTransferScoutProject.auth.security.AccountDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api")
public class AuctionController {

    @Autowired
    AuctionRepository auctionRepository;
    
    @Autowired
    AuctionService auctionService;



    // 전체 Auction 리턴
    @GetMapping("/auction")
    public ResponseEntity<List<AuctionDto>> allAuction() {
        Iterable<Auction> auctions = auctionRepository.findAll();
        List<AuctionDto> auctionDtos = auctionService.toDtoList(auctions); // toDtoList 메소드에서 List를 생성하도록 수정

        return ResponseEntity.ok(auctionDtos);
    }


    // 동적 라우팅으로 인한 단일 게시판 리턴
    @GetMapping("/auction/{id}")
    public ResponseEntity<AuctionDto> idAuction(@PathVariable("id") Long id) {
        Auction auction = auctionRepository.findById(id).orElse(null);
        AuctionDto auctionDto = auctionService.toDto(auction);

        return ResponseEntity.ok(auctionDto);

    }

    // 경매글 생성
    @PostMapping("/auction/create")
    public ResponseEntity<?> createAuction(@RequestBody AuctionDto auctionDto, @AuthenticationPrincipal AccountDetailsImpl accountDetails) {
        AuctionDto auctionDto1 = auctionService.createAuction(auctionDto, accountDetails);
        return ResponseEntity.ok(auctionDto1);
    }

}
