package com.example.DWTransferScoutProject.auth.security;

import com.example.DWTransferScoutProject.common.account.entity.BaseAccount;
import com.example.DWTransferScoutProject.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class AccountDetailsImpl implements UserDetails {
    private final BaseAccount account;
    private final String password;
    private final String accountId;
    private final ApplicationRoleEnum role;

    public AccountDetailsImpl(BaseAccount account) {
        this.account = account;
        this.password = account.getPassword();
        this.accountId = account.getAccountId();
        this.role = account.getAccountRole();
    }


    public BaseAccount getAccount() {
        return this.account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String authority = role.getAuthority();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.accountId; // accountId로 변경
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
