package com.example.DWTransferScoutProject.superadmin.service;

import com.example.DWTransferScoutProject.auth.security.ApplicationRoleEnum;
import com.example.DWTransferScoutProject.auth.service.AuthService;
import com.example.DWTransferScoutProject.superadmin.dto.SuperAdminDto;
import com.example.DWTransferScoutProject.superadmin.entity.SuperAdmin;
import com.example.DWTransferScoutProject.superadmin.repository.SuperAdminRepository;
import com.example.DWTransferScoutProject.common.account.service.BaseAccountService;
import com.example.DWTransferScoutProject.user.dto.UserDto;
import com.example.DWTransferScoutProject.user.entity.User;
import com.example.DWTransferScoutProject.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SuperAdminService implements BaseAccountService<SuperAdmin, SuperAdminDto> {
    private final SuperAdminRepository superAdminRepository;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 메서드
    @Override
    public SuperAdmin signUp(SuperAdminDto superAdminDto) {
        SuperAdmin superAdmin = SuperAdmin.builder()
                .accountId(superAdminDto.getSuperAdminId())
                .password(passwordEncoder.encode(superAdminDto.getPassword()))
                .email(superAdminDto.getEmail())
                .accountType(ApplicationRoleEnum.SUPER_ADMIN)
                .build();
        return saveAccount(superAdmin);
    }

    private SuperAdmin saveAccount(SuperAdmin superAdmin) {
        if (superAdminRepository.findByAccountId(superAdmin.getAccountId()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다: " + superAdmin.getAccountId());
        }
        return superAdminRepository.save(superAdmin);
    }

    public void updatePassword(Long id, String newPassword) {
        SuperAdmin superAdmin = superAdminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("SuperAdmin not found"));
        superAdmin.updatePassword(passwordEncoder.encode(newPassword));
        superAdminRepository.save(superAdmin);
    }

    @Override
    public void deleteAccount(Long id) {
        superAdminRepository.deleteById(id);
    }

    @Override
    public SuperAdminDto updateAccount(Long id, SuperAdminDto superAdminDto) {
        SuperAdmin existingSuperAdmin = superAdminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));

        existingSuperAdmin.updateSuperAdminInfo(
                superAdminDto.getEmail(),
                superAdminDto.getAccountType()
        );

        SuperAdmin updatedSuperAdmin = superAdminRepository.save(existingSuperAdmin);
        return mapToDTO(updatedSuperAdmin);
    }

    public SuperAdminDto edit(SuperAdminDto adminDto) {
        return updateAccount(adminDto.getId(), adminDto);
    }

    public void updatePassword(SuperAdmin superAdmin, String newPassword, String currentSuperAdmin) {
        if (!superAdmin.getEmail().equals(currentSuperAdmin)) {
            throw new SecurityException("You do not have permission to change this password");
        }

        if (newPassword != null && !newPassword.isEmpty()) {
            // 기존 비밀번호와 다른지 확인
            if (!passwordEncoder.matches(newPassword, superAdmin.getPassword())) {
                // 비밀번호 해시화
                String encodedPassword = passwordEncoder.encode(newPassword);
                superAdmin.updatePassword(encodedPassword);
            } else {
                throw new IllegalArgumentException("New password cannot be the same as the current password");
            }
        } else {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }

    @Override
    public Optional<SuperAdmin> findById(Long id) {
        return superAdminRepository.findById(id);
    }

    @Override
    public Optional<SuperAdmin> findByAccountId(String accountId) {
        return superAdminRepository.findByAccountId(accountId);
    }

    @Override
    public SuperAdminDto mapToDTO(SuperAdmin admin) {
        return SuperAdminDto.builder()
                .id(admin.getId())
                .superAdminId(admin.getAccountId())
                .password(null) // 비밀번호는 반환하지 않음
                .email(admin.getEmail())
                .accountType(admin.getAccountType())
                .build();
    }

    // 관리자가 사용자 정보를 업데이트하는 메서드
    public UserDto SuperAdminupdateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (userDto.getAccountType() != null) {
            existingUser.updateAccountType(userDto.getAccountType());
        }

        User updatedUser = userRepository.save(existingUser);
        return mapToUserDTO(updatedUser);
    }

    public UserDto mapToUserDTO(User user) {
        return UserDto.builder()
                .id(user.getId())
                .accountId(user.getAccountId())
                .accountType(user.getAccountType())
                .username(user.getUsername())
                .password(null)
                .birthdate(user.getBirthdate())
                .gender(user.getGender())
                .email(user.getEmail())
                .contact(user.getContact())
                .build();
    }
}
