package com.example.DWTransferScoutProject.common.account.repository;

import com.example.DWTransferScoutProject.common.account.entity.BaseAccount;
import java.util.Optional;

public interface BaseAccountRepository<T extends BaseAccount> {
    Optional<T> findByAccountId(String accountId);
}
