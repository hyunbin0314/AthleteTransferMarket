package com.example.DWTransferScoutProject.superadmin.service;

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
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SuperAdmin createAccount(SuperAdminDto superAdminDto) {
        SuperAdmin superAdmin = SuperAdmin.builder()
                .accountId(superAdminDto.getAccountId())
                .password(superAdminDto.getPassword())
                .email(superAdminDto.getEmail())
                .accountRole(superAdminDto.getAccountRole())
                .build();

        return superAdminRepository.save(superAdmin); // Save the super admin entity to the repository
    }

    @Override
    public SuperAdminDto saveAccount(SuperAdminDto superAdminDto) {
        SuperAdmin superAdmin = createAccount(superAdminDto); // Create the SuperAdmin entity
        return new SuperAdminDto(superAdmin); // Map the SuperAdmin entity to SuperAdminDto and return it
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
                superAdminDto.getAccountRole()
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
    public SuperAdminDto mapToDTO(SuperAdmin superAdmin) {
        return SuperAdminDto.builder()
                .id(superAdmin.getId())
                .accountId(superAdmin.getAccountId())
                .password(null) // 비밀번호는 반환하지 않음
                .email(superAdmin.getEmail())
                .accountRole(superAdmin.getAccountRole())
                .build();
    }

    // 관리자가 사용자 정보를 업데이트하는 메서드
    public UserDto SuperAdminupdateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (userDto.getAccountRole() != null) {
            existingUser.updateAccountRole(userDto.getAccountRole());
        }

        User updatedUser = userRepository.save(existingUser);
        return mapToUserDTO(updatedUser);
    }

    public UserDto mapToUserDTO(User user) {
        return UserDto.builder()
                .id(user.getId())
                .accountId(user.getAccountId())
                .accountRole(user.getAccountRole())
                .username(user.getUsername())
                .password(null)
                .birthdate(user.getBirthdate())
                .gender(user.getGender())
                .email(user.getEmail())
                .contact(user.getContact())
                .build();
    }
}
