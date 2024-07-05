package com.example.DWTransferScoutProject.auction.dto;

import com.example.DWTransferScoutProject.bid.dto.BidDto;
import com.example.DWTransferScoutProject.successfulbid.dto.SuccessfulBidDto;
import com.example.DWTransferScoutProject.user.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionDto {
    private Long id;
    private UserDto userDto;
    private List<BidDto> bidDtos;
    private SuccessfulBidDto successfulBid;
    private String title;
    private String contents;
    private int transferFee;
    private LocalDateTime createTime;
    private LocalDateTime deadline;
}