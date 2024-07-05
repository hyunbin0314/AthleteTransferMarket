package com.example.DWTransferScoutProject.superadmin.entity;

import com.example.DWTransferScoutProject.auth.security.ApplicationRoleEnum;
import com.example.DWTransferScoutProject.common.account.entity.BaseAccount;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "superadmins")
public class SuperAdmin implements BaseAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(value = EnumType.STRING)
    private ApplicationRoleEnum accountType;

    @Builder
    public SuperAdmin(ApplicationRoleEnum accountType, String accountId, String password, String email) {
        this.accountType = accountType;
        this.accountId = accountId;
        this.password = password;
        this.email = email;
    }

    public void updateSuperAdminInfo(String email, ApplicationRoleEnum accountType) {
        if (email != null) {
            this.email = email;
        }
        if (accountType != null) {
            this.accountType = accountType;
        }
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
