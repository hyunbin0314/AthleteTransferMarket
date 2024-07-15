package com.example.DWTransferScoutProject.user.entity;

import com.example.DWTransferScoutProject.auction.entity.Auction;
import com.example.DWTransferScoutProject.auth.security.ApplicationRoleEnum;
import com.example.DWTransferScoutProject.common.account.entity.BaseAccount;
import com.example.DWTransferScoutProject.playertransfer.entity.PlayerTransfer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Setter
public class User implements BaseAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountId; // 사이트에서 사용하는 ID

    @JsonIgnore
    private String password;

    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ApplicationRoleEnum accountType;

    @Column(nullable = false)
    private String username; // 회원의 본명

    private String birthdate;

    @Enumerated(value = EnumType.STRING)
    private GenderEnum gender;

    private String contact;

    @OneToMany(mappedBy = "user") // 수정된 부분
    private List<Auction> auctions;

    @OneToMany(mappedBy = "user")
    private List<PlayerTransfer> playerTransfers;




    @Builder
    public User(ApplicationRoleEnum accountType, String accountId, String username, String password,
                String birthdate, GenderEnum gender, String email, String contact) {
        this.accountType = accountType;
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.birthdate = birthdate;
        this.gender = gender;
        this.email = email;
        this.contact = contact;
    }

    public void updateUserInfo(String username, String birthdate, GenderEnum gender, String userEmail, String contact) {
        if (username != null) this.username = username;
        if (birthdate != null) this.birthdate = birthdate;
        if (gender != null) this.gender = gender;
        if (userEmail != null) this.email = userEmail;
        if (contact != null) this.contact = contact;
    }

    @Override
    public void updatePassword(String password) {
        if (password != null) {
            this.password = password;
        }
    }

    public void updateAccountType(ApplicationRoleEnum userType) {
        if (userType != null) {
            this.accountType = userType;
        }
    }

    @Override
    public String getAccountId() {
        return this.accountId;
    }

    @Override
    public ApplicationRoleEnum getAccountRole() {
        return this.accountType;
    }
}
