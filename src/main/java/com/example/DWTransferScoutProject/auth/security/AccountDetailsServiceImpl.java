package com.example.DWTransferScoutProject.auth.security;

import com.example.DWTransferScoutProject.common.account.entity.BaseAccount;
import com.example.DWTransferScoutProject.user.entity.User;
import com.example.DWTransferScoutProject.superadmin.entity.SuperAdmin;
import com.example.DWTransferScoutProject.user.repository.UserRepository;
import com.example.DWTransferScoutProject.superadmin.repository.SuperAdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final SuperAdminRepository adminRepository;

    public AccountDetailsServiceImpl(UserRepository userRepository, SuperAdminRepository adminRepository) {
        super();
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException { // username을 accountId로 변경
        User user = userRepository.findByAccountId(accountId).orElse(null);
        SuperAdmin admin = adminRepository.findByAccountId(accountId).orElse(null);

        BaseAccount account = user != null ? user : admin;

        if (account != null) {
            return new AccountDetailsImpl(account);
        } else {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }

    public User findUserEntityByUserId(String userId) throws UsernameNotFoundException {
        return userRepository.findByAccountId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    public SuperAdmin findAdminEntityByAdminId(String adminId) throws UsernameNotFoundException {
        return adminRepository.findByAccountId(adminId)
                .orElseThrow(() -> new UsernameNotFoundException("관리자를 찾을 수 없습니다."));
    }
}
