package com.example.DWTransferScoutProject.auction.entity;

import com.example.DWTransferScoutProject.bid.entity.Bid;
import com.example.DWTransferScoutProject.successfulbid.entity.SuccessfulBid;
import com.example.DWTransferScoutProject.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    private String title;
    private String contents;

    private int transferFee;

    private LocalDateTime createTime;

    private LocalDateTime deadline;
    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bidList;

    @OneToOne(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    private SuccessfulBid successfulBid;

}
