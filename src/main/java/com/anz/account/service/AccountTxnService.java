package com.anz.account.service;

import com.anz.account.dto.AccountTxnDto;

import java.util.List;

public interface AccountTxnService {
    void createTxns(List<AccountTxnDto> list);

    List<AccountTxnDto> getAllTxns(String accountNumber);
}
