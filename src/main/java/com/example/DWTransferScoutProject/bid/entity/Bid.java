package com.example.DWTransferScoutProject.bid.entity;

import com.example.DWTransferScoutProject.auction.entity.Auction;
import com.example.DWTransferScoutProject.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Auction auction;

    @ManyToOne
    private User user;

    private LocalDateTime bidTime;

    private int TransferFee;


}
