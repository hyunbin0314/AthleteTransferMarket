package com.example.DWTransferScoutProject.playertransfer.entity;


import com.example.DWTransferScoutProject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerTransfer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "user_id" )
    private User user;

    private Date contractDate;

    private Date expireDate;

    private String club;

    private int transferFee;
}
