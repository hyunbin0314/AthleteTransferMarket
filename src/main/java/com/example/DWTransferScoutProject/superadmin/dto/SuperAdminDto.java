package com.example.DWTransferScoutProject.superadmin.dto;

import com.example.DWTransferScoutProject.auth.security.ApplicationRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuperAdminDto {
    private Long id;
    private String superAdminId;
    private String password;
    private String email;
    private ApplicationRoleEnum accountType;
}
