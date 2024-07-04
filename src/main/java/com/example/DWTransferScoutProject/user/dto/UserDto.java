package com.example.DWTransferScoutProject.user.dto;

import com.example.DWTransferScoutProject.auth.security.ApplicationRoleEnum;
import com.example.DWTransferScoutProject.user.entity.GenderEnum;
import com.example.DWTransferScoutProject.user.entity.User;
import com.example.DWTransferScoutProject.address.dto.AddressDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String userId;
    private String username;
    private String password;
    private String confirmPassword;
    private String birthdate;
    private GenderEnum gender;
    private String email;
    private String contact;
    private AddressDto address;
    private ApplicationRoleEnum accountType;

    public UserDto(User user) {
        this.id = user.getId();
        this.userId = user.getAccountId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.birthdate = user.getBirthdate();
        this.gender = user.getGender();
        this.email = user.getEmail();
        this.contact = user.getContact();
        this.address = new AddressDto(user.getAddress());
        this.accountType = user.getAccountType();
    }
}
