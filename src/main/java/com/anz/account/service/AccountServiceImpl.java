package com.anz.account.service;

import com.anz.account.dto.AccountDto;
import com.anz.account.entity.Account;
import com.anz.account.enums.AccountTypeEnum;
import com.anz.account.enums.CurrencyEnum;
import com.anz.account.repo.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Long create(AccountDto dto) {
        Account account = new Account();
        account.setAccountBalance(dto.getAccountBalance());
        account.setAccountName(dto.getAccountName());
        account.setAccountNumber(dto.getAccountNumber());
        account.setCurrency(dto.getCurrency().name());
        account.setType(dto.getAccountType().name());

        return accountRepository.save(account).getId();
    }

    @Override
    public AccountDto getAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);

        if (account == null) {
            log.error("Account number not found");
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Account number not found");
        }

        AccountDto dto = null;

        if (null != account) {
            dto = new AccountDto();
            if (null != account.getAccountBalance())
                dto.setAccountBalance(account.getAccountBalance());
            else
                dto.setAccountBalance(new BigDecimal(0));
            dto.setAccountName(account.getAccountName());
            dto.setAccountNumber(account.getAccountNumber());
            dto.setBalanceDate(OffsetDateTime.now());
            dto.setCurrency(CurrencyEnum.valueOf(account.getCurrency()));
            dto.setAccountType(AccountTypeEnum.valueOf(account.getType()));
            dto.setId(account.getId());
        }

        return dto;
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> list = accountRepository.findAll();

        List<AccountDto> dtoList = new ArrayList<>();

        for (Account account : list) {
            AccountDto dto = new AccountDto();
            if (null != account.getAccountBalance())
                dto.setAccountBalance(account.getAccountBalance());
            else
                dto.setAccountBalance(new BigDecimal(0));
            dto.setAccountName(account.getAccountName());
            dto.setAccountNumber(account.getAccountNumber());
            dto.setBalanceDate(OffsetDateTime.now());
            dto.setCurrency(CurrencyEnum.valueOf(account.getCurrency()));
            dto.setAccountType(AccountTypeEnum.valueOf(account.getType()));
            dto.setId(account.getId());
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public AccountDto updateAccount(String accountNumber, AccountDto dto) {
        Account account = new Account();
        account.setAccountBalance(dto.getAccountBalance());
        account.setAccountName(dto.getAccountName());
        account.setAccountNumber(dto.getAccountNumber());
        account.setCurrency(dto.getCurrency().name());
        account.setType(dto.getAccountType().name());
        account.setBalanceDate(OffsetDateTime.now());
        account.setId(dto.getId());
        Account savedAccount = accountRepository.save(account);

        AccountDto updateddto = new AccountDto();


        if (null != account.getAccountBalance())
            updateddto.setAccountBalance(account.getAccountBalance());
        else
            updateddto.setAccountBalance(new BigDecimal(0));
        updateddto.setAccountName(account.getAccountName());
        updateddto.setAccountNumber(account.getAccountNumber());
        updateddto.setBalanceDate(OffsetDateTime.now());
        updateddto.setCurrency(CurrencyEnum.valueOf(account.getCurrency()));
        updateddto.setAccountType(AccountTypeEnum.valueOf(account.getType()));
        updateddto.setId(account.getId());

        return updateddto;
    }
}
