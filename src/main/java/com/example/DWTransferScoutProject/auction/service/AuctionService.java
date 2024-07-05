package com.example.DWTransferScoutProject.auction.service;


import com.example.DWTransferScoutProject.auction.dto.AuctionDto;
import com.example.DWTransferScoutProject.auction.entity.Auction;
import com.example.DWTransferScoutProject.auction.repository.AuctionRepository;
import com.example.DWTransferScoutProject.auth.security.AccountDetailsImpl;
import com.example.DWTransferScoutProject.common.account.entity.BaseAccount;
import com.example.DWTransferScoutProject.user.dto.UserDto;
import com.example.DWTransferScoutProject.user.entity.User;
import com.example.DWTransferScoutProject.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AuctionService {

private final AuctionRepository auctionRepository;
private final UserRepository userRepository;


public AuctionDto createAuction(AuctionDto auctionDto, AccountDetailsImpl accountDetails) {
    Auction auction = new Auction();
    auction.setTitle(auctionDto.getTitle());
    auction.setContents(auctionDto.getContents());
    auction.setCreateTime(LocalDateTime.now());
    auction.setDeadline(LocalDateTime.now().plusDays(7));
    auction.setTransferFee(auctionDto.getTransferFee());



    BaseAccount account = accountDetails.getAccount();
    User user = (User) account;


    auction.setUser(user);
    auction.setTransferFee(auction.getTransferFee());

    auctionRepository.save(auction);

    AuctionDto auctionDto1 = toDto(auction);



    return auctionDto1;
}




    // Iterable<Auction>를 List<AuctionDto>로 변환
    // 멤버속성은 오직 UserId만 반환한다.
    public List<AuctionDto> toDtoList(Iterable<Auction> auctions) {
        List<AuctionDto> auctionDtos = new ArrayList<>();
        for (Auction auction : auctions) {
            AuctionDto auctionDto = new AuctionDto();
            auctionDto.setId(auction.getId());
            auctionDto.setTitle(auction.getTitle());
            auctionDto.setContents(auction.getContents());
            auctionDto.setCreateTime(auction.getCreateTime());
            auctionDto.setDeadline(auction.getDeadline());
            auctionDto.setTransferFee(auction.getTransferFee());

            User user = userRepository.findById(auction.getUser().getId()).orElse(null);
            if (user != null) {
                UserDto userDto = new UserDto();
                userDto.setAccountId(user.getAccountId());
                auctionDto.setUserDto(userDto);
            } else {
                // 처리할 회원이 없는 경우에 대한 예외 처리
                // 혹은 다른 방법으로 처리할 수 있음
            }

            auctionDtos.add(auctionDto);
        }
        return auctionDtos;
    }


    // Auction를 AuctionDto로 반환
    // 멤버속성은 오직 UserId만 반환한다.
    public AuctionDto toDto(Auction auction) {
        AuctionDto auctionDto = new AuctionDto();
        auctionDto.setId(auction.getId());
        auctionDto.setTitle(auction.getTitle());
        auctionDto.setContents(auction.getContents());
        auctionDto.setCreateTime(auction.getCreateTime());
        auctionDto.setDeadline(auction.getDeadline());
        auctionDto.setTransferFee(auction.getTransferFee());

        User user = auction.getUser();
        UserDto userDto = new UserDto();
        userDto.setAccountId(user.getAccountId());

        auctionDto.setUserDto(userDto);

        return auctionDto;

    }
}
