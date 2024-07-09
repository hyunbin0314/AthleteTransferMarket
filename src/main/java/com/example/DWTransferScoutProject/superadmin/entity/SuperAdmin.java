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
    private ApplicationRoleEnum accountRole;

    @Builder
    public SuperAdmin(ApplicationRoleEnum accountRole, String accountId, String password, String email) {
        this.accountRole = accountRole;
        this.accountId = accountId;
        this.password = password;
        this.email = email;
    }

    public void updateSuperAdminInfo(String email, ApplicationRoleEnum accountRole) {
        if (email != null) {
            this.email = email;
        }
        if (accountRole != null) {
            this.accountRole = accountRole;
        }
    }

    @Override
    public void updatePassword(String password) {
        if (password != null) {
            this.password = password;
        }
    }

    public void updateAccountRole(ApplicationRoleEnum accountRole) {
        if (accountRole != null) {
            this.accountRole = accountRole;
        }
    }

    @Override
    public String getAccountId() {
        return this.accountId;
    }

    @Override
    public ApplicationRoleEnum getAccountRole() {
        return this.accountRole;
    }
}
