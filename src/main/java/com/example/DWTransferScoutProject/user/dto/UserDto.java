package com.example.DWTransferScoutProject.user.dto;

import com.example.DWTransferScoutProject.auth.security.ApplicationRoleEnum;
import com.example.DWTransferScoutProject.common.account.dto.BaseAccountDto;
import com.example.DWTransferScoutProject.user.entity.GenderEnum;
import com.example.DWTransferScoutProject.user.entity.User;
import com.example.DWTransferScoutProject.address.dto.AddressDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements BaseAccountDto {
    private Long id;
    private String accountId;
    private String password;
    private String confirmPassword;
    private String email;
    private ApplicationRoleEnum accountRole;
    private String username;
    private String birthdate;
    private GenderEnum gender;
    private String contact;
    private AddressDto address;

    public UserDto(User user) {
        this.id = user.getId();
        this.accountId = user.getAccountId();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.accountRole = user.getAccountRole();
        this.username = user.getUsername();
        this.birthdate = user.getBirthdate();
        this.gender = user.getGender();
        this.contact = user.getContact();
        this.address = user.getAddress() != null ? new AddressDto(user.getAddress()) : null;
    }
}
