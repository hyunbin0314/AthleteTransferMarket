package com.example.DWTransferScoutProject.auth.service;

import com.example.DWTransferScoutProject.address.dto.AddressDto;
import com.example.DWTransferScoutProject.address.entity.Address;
import com.example.DWTransferScoutProject.address.service.AddressService;
import com.example.DWTransferScoutProject.common.account.dto.BaseAccountDto;
import com.example.DWTransferScoutProject.common.account.dto.LoginDto;
import com.example.DWTransferScoutProject.common.account.service.BaseAccountService;
import com.example.DWTransferScoutProject.superadmin.entity.SuperAdmin;
import com.example.DWTransferScoutProject.superadmin.repository.SuperAdminRepository;
import com.example.DWTransferScoutProject.user.dto.UserDto;
import com.example.DWTransferScoutProject.user.entity.User;
import com.example.DWTransferScoutProject.user.repository.UserRepository;
import com.example.DWTransferScoutProject.auth.security.JwtUtil;
import com.example.DWTransferScoutProject.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final SuperAdminRepository superAdminRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AddressService addressService;

    @Transactional
    public <T extends BaseAccountDto> T signUp(T accountDto, BaseAccountService<?, T> accountService) {
        if (accountService.findByAccountId(accountDto.getAccountId()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다: " + accountDto.getAccountId());
        }

        if (accountDto.getPassword() != null && !accountDto.getPassword().equals(accountDto.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        String encodedPassword = passwordEncoder.encode(accountDto.getPassword());
        accountDto.setPassword(encodedPassword);

        // 주소 저장
        if (accountDto instanceof UserDto) {
            UserDto userDto = (UserDto) accountDto;
            Address address = null;
            if (userDto.getAddress() != null) {
                address = addressService.saveAddress(userDto.getAddress());
            }
            userDto.setAddress(address != null ? new AddressDto(address) : null);
        }

        return accountService.saveAccount(accountDto);
    }

    @Transactional
    public String login(LoginDto loginDto, HttpServletResponse response) {
        Optional<User> optionalUser = userRepository.findByAccountId(loginDto.getAccountId());

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("회원이 존재하지 않음");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(user.getAccountId(), user.getAccountRole());

        return token;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserByUsernameAndEmail(String username, String email) {
        return userRepository.findByUsernameAndEmail(username, email);
    }

    public List<User> findUsersByEmail(String email) {
        return userRepository.findAllByEmail(email);
    }

    public void passwordResetByEmail(User user, int verificationCode) {
        user.updatePassword(passwordEncoder.encode(String.valueOf(verificationCode)));
        userRepository.save(user);
    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findByAccountId(userId);
    }
}
