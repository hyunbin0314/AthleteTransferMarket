package com.example.DWTransferScoutProject.bid.service;


import com.example.DWTransferScoutProject.auction.dto.AuctionDto;
import com.example.DWTransferScoutProject.auction.entity.Auction;
import com.example.DWTransferScoutProject.auction.service.AuctionService;
import com.example.DWTransferScoutProject.auth.security.AccountDetailsImpl;
import com.example.DWTransferScoutProject.bid.dto.BidDto;
import com.example.DWTransferScoutProject.bid.entity.Bid;
import com.example.DWTransferScoutProject.bid.repository.BidRepository;
import com.example.DWTransferScoutProject.user.dto.UserDto;
import com.example.DWTransferScoutProject.user.entity.User;
import com.example.DWTransferScoutProject.user.repository.UserRepository;
import com.example.DWTransferScoutProject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuctionService auctionService;



    //입찰기록 생성
    public Bid createbid(Auction auction, AccountDetailsImpl accountDetails) {
        Bid bid = new Bid();
        bid.setUser(userRepository.findById(accountDetails.getAccount().getId()).get());
        bid.setAuction(auction);
        bid.setTransferFee(auction.getTransferFee());
        bid.setBidTime(LocalDateTime.now());
        bidRepository.save(bid);
        return bid;
    }

    // Bid타입을 가지는 복수객체를 BidDto타입을 가지는 복수객체로 변환 대신 로그인한 사용자와 정보를 같은 데이터만 리턴
    public List<BidDto> toDtoList(List<Bid> bidList) {
        List<BidDto> bidDtoList = new ArrayList<>();

        for (Bid bid : bidList) {
            BidDto bidDto = toDto(bid);

                bidDtoList.add(bidDto);
        }

        return bidDtoList;
    }

    // Bid단일객체 Dto로 변환
    public BidDto toDto(Bid bid) {
        BidDto bidDto = new BidDto();
        bidDto.setId(bid.getId());
        bidDto.setTransferFee(bid.getTransferFee());
        bidDto.setBidTime(bid.getBidTime());
        bidDto.setUserDto(userService.mapToDTO(bid.getUser()));
        bidDto.setAuctionDto(auctionService.toDto(bid.getAuction()));

        return bidDto;
    }

    // 내 bid기록중에 게시글당 가장 최근 입찰기록을 반환
    public List<BidDto> getUniqueBids(List<BidDto> bidDtoList) {
        Map<Long, BidDto> uniqueBidsMap = new HashMap<>();

        for (BidDto bidDto : bidDtoList) {
            Long auctionId = bidDto.getAuctionDto().getId();

            // 중복된 Auction ID가 없으면 추가합니다.
            if (!uniqueBidsMap.containsKey(auctionId)) {
                uniqueBidsMap.put(auctionId, bidDto);
            } else {
                // 이미 존재하는 경우, 현재 BidDto의 ID가 더 큰 경우에만 교체
                if (bidDto.getId() > uniqueBidsMap.get(auctionId).getId()) {
                    uniqueBidsMap.put(auctionId, bidDto);
                }
            }
        }

        return new ArrayList<>(uniqueBidsMap.values());
    }
}
