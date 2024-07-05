package com.example.DWTransferScoutProject.bid.dto;

import com.example.DWTransferScoutProject.auction.dto.AuctionDto;
import com.example.DWTransferScoutProject.user.dto.UserDto;
import com.example.DWTransferScoutProject.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidDto {
    private Long id;
    private AuctionDto auctionDto;
    private UserDto userDto;
    private LocalDateTime bidTime;
    private int transferFee;
}