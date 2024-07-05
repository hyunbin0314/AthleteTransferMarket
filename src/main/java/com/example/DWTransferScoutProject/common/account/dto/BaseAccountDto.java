package com.example.DWTransferScoutProject.common.account.dto;

import com.example.DWTransferScoutProject.auth.security.ApplicationRoleEnum;

public interface BaseAccountDto {
    Long getId();
    String getAccountId();
    String getPassword();
    String getConfirmPassword();
    String getEmail();
    ApplicationRoleEnum getAccountRole();
    void setPassword(String password);
    void setConfirmPassword(String confirmPassword);
    void setAccountId(String accountId);
    void setEmail(String email);
    void setAccountRole(ApplicationRoleEnum accountRole);
}
