package com.example.DWTransferScoutProject.superadmin.dto;

import com.example.DWTransferScoutProject.auth.security.ApplicationRoleEnum;
import com.example.DWTransferScoutProject.common.account.dto.BaseAccountDto;
import com.example.DWTransferScoutProject.superadmin.entity.SuperAdmin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuperAdminDto implements BaseAccountDto {
    private Long id;
    private String accountId;
    private String password;
    private String confirmPassword;
    private String email;
    private ApplicationRoleEnum accountRole;

    public SuperAdminDto(SuperAdmin superAdmin) {
        this.id = superAdmin.getId();
        this.accountId = superAdmin.getAccountId();
        this.password = superAdmin.getPassword();
        this.email = superAdmin.getEmail();
        this.accountRole = superAdmin.getAccountRole();
    }
}
