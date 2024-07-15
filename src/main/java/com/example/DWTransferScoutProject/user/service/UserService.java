package com.example.DWTransferScoutProject.user.service;

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
    private final AuthService authService;

    // 회원가입 메서드
    @Override
    public User signUp(UserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
        User user = User.builder()
                .accountId(userDto.getAccountId())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .birthdate(userDto.getBirthdate())
                .gender(userDto.getGender())
                .email(userDto.getEmail())
                .contact(userDto.getContact())
                .accountType(ApplicationRoleEnum.USER)
                .build();
        return saveAccount(user);
    }

    private User saveAccount(User user) {
        if (userRepository.findByAccountId(user.getAccountId()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다: " + user.getAccountId());
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteAccount(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto updateAccount(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        // 엔티티의 업데이트 메서드를 사용하여 정보 변경
        existingUser.updateUserInfo(
                userDto.getUsername(),
                userDto.getBirthdate(),
                userDto.getGender(),
                userDto.getEmail(),
                userDto.getContact()

        );

        if (userDto.getAccountType() != null) {
            existingUser.updateAccountType(userDto.getAccountType());
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
                .accountType(user.getAccountType())
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
