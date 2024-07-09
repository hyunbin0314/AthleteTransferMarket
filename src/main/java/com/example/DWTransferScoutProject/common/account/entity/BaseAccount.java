package com.example.DWTransferScoutProject.common.account.entity;

import com.example.DWTransferScoutProject.auth.security.ApplicationRoleEnum;

public interface BaseAccount {
    Long getId();
    String getAccountId();
    String getPassword();
    ApplicationRoleEnum getAccountRole();
    void updatePassword(String password);
}
