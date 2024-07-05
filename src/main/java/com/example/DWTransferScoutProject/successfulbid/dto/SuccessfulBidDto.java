package com.example.DWTransferScoutProject.successfulbid.dto;

import com.example.DWTransferScoutProject.auction.dto.AuctionDto;
import com.example.DWTransferScoutProject.user.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuccessfulBidDto {
    private Long id;
    private AuctionDto auctionDto;
    private User user;
}
