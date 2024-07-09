package com.example.DWTransferScoutProject.common.account.dto;

import com.example.DWTransferScoutProject.auth.security.ApplicationRoleEnum;
import com.example.DWTransferScoutProject.superadmin.dto.SuperAdminDto;
import com.example.DWTransferScoutProject.user.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserDto.class, name = "user"),
        @JsonSubTypes.Type(value = SuperAdminDto.class, name = "superadmin")
})
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
