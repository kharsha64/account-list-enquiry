package com.anz.account.repo;

import com.anz.account.entity.AccountTxn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountTxnRepo extends JpaRepository<AccountTxn, Long> {
    List<AccountTxn> findByAccountNumber(String accountNumber);
}
