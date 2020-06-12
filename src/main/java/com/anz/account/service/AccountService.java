package com.anz.account.service;

import com.anz.account.dto.AccountDto;

import java.util.List;

public interface AccountService {
    Long create(AccountDto dto);

    AccountDto getAccount(String accountNumber);

    List<AccountDto> getAllAccounts();

    AccountDto updateAccount(String accountNumber, AccountDto dto);
}
