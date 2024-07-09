package com.example.DWTransferScoutProject.user.service;

import com.example.DWTransferScoutProject.address.dto.AddressDto;
import com.example.DWTransferScoutProject.address.entity.Address;
import com.example.DWTransferScoutProject.address.service.AddressService;
import com.example.DWTransferScoutProject.auth.security.ApplicationRoleEnum;
import com.example.DWTransferScoutProject.auth.service.AuthService;
import com.example.DWTransferScoutProject.common.account.service.BaseAccountService;
import com.example.DWTransferScoutProject.user.dto.UserDto;
import com.example.DWTransferScoutProject.user.entity.User;
import com.example.DWTransferScoutProject.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService implements BaseAccountService<User, UserDto> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressService addressService;

    @Override
    public User createAccount(UserDto userDto) {
        Address address = null;
        if (userDto.getAddress() != null) {
            address = addressService.saveAddress(userDto.getAddress());
        }

        User user = User.builder()
                .accountId(userDto.getAccountId())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .accountRole(ApplicationRoleEnum.USER)
                .username(userDto.getUsername())
                .birthdate(userDto.getBirthdate())
                .gender(userDto.getGender())
                .contact(userDto.getContact())
                .address(address) // AddressService를 활용하여 변환된 Address 사용
                .build();

        return userRepository.save(user); // Save the user entity to the repository
    }

    @Override
    public UserDto saveAccount(UserDto userDto) {
        User user = createAccount(userDto); // Create the User entity
        return new UserDto(user); // Map the User entity to UserDto and return it
    }

    @Override
    public void deleteAccount(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto updateAccount(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // AddressDto를 Address 엔티티로 변환
        Address address = null;
        if (userDto.getAddress() != null) {
            address = addressService.saveAddress(userDto.getAddress()); // AddressService의 메서드 사용
        }

        // 엔티티의 업데이트 메서드를 사용하여 정보 변경
        existingUser.updateUserInfo(
                userDto.getUsername(),
                userDto.getBirthdate(),
                userDto.getGender(),
                userDto.getEmail(),
                userDto.getContact(),
                address
        );

        if (userDto.getAccountRole() != null) {
            existingUser.updateAccountRole(userDto.getAccountRole());
        }

        User updatedUser = userRepository.save(existingUser);
        return mapToDTO(updatedUser);
    }

    public UserDto edit(UserDto userDto) {
        return updateAccount(userDto.getId(), userDto);
    }

    public void updatePassword(User user, String newPassword, String currentUser) {
        if (!user.getUsername().equals(currentUser)) {
            throw new SecurityException("You do not have permission to change this password");
        }

        if (newPassword != null && !newPassword.isEmpty()) {
            // 기존 비밀번호와 다른지 확인
            if (!passwordEncoder.matches(newPassword, user.getPassword())) {
                // 비밀번호 해시화
                String encodedPassword = passwordEncoder.encode(newPassword);
                user.updatePassword(encodedPassword);
            } else {
                throw new IllegalArgumentException("New password cannot be the same as the current password");
            }
        } else {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByAccountId(String accountId) {
        return userRepository.findByAccountId(accountId);
    }

    @Override
    public UserDto mapToDTO(User user) {
        return UserDto.builder()
                .id(user.getId())
                .accountId(user.getAccountId())
                .username(user.getUsername())
                .password(null)
                .birthdate(user.getBirthdate())
                .gender(user.getGender())
                .email(user.getEmail())
                .contact(user.getContact())
                .address(user.getAddress() != null ? new AddressDto(user.getAddress()) : null)
                .accountRole(user.getAccountRole())
                .build();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findUsersByEmail(String email) {
        return userRepository.findAllByEmail(email);
    }
}
