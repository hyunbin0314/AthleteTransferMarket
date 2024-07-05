package com.example.DWTransferScoutProject.successfulbid.entity;

import com.example.DWTransferScoutProject.auction.entity.Auction;
import com.example.DWTransferScoutProject.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "successfulBid")
public class SuccessfulBid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "id")
    private Auction auction;

    @ManyToOne
    private User user;


}
