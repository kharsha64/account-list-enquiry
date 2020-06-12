package com.anz.account.service;

import com.anz.account.client.AccountClient;
import com.anz.account.dto.AccountDto;
import com.anz.account.dto.AccountTxnDto;
import com.anz.account.enums.CreditDebitEnum;
import com.anz.account.enums.CurrencyEnum;
import com.anz.account.entity.AccountTxn;
import com.anz.account.repo.AccountTxnRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccountTxnServiceImpl implements AccountTxnService{

    @Autowired
    private AccountTxnRepo txnRepo;

    @Autowired
    private AccountClient client;

    @Override
    public void createTxns(List<AccountTxnDto> list) {
        for (AccountTxnDto dto : list) {
            AccountDto accountDto = null;
            try {
                accountDto = client.getAccount(dto.getAccountNumber());
            } catch (HttpClientErrorException e) {
                log.error("Not a valid account number : ", e.getMessage());
                throw e;
            }

            AccountTxn txn = new AccountTxn();
            txn.setAccountName(dto.getAccountName());
            txn.setAccountNumber(dto.getAccountNumber());
            txn.setCreditDebit(dto.getCreditDebit().name());

            if (dto.getCreditDebit().name().equals(CreditDebitEnum.CREDIT.name())) {
                txn.setCreditAmount(dto.getCreditAmount());
                txn.setDebitAmount(new BigDecimal(0));
                accountDto.setAccountBalance(accountDto.getAccountBalance().add(dto.getCreditAmount()));
            } else {
                txn.setDebitAmount(dto.getDebitAmount());
                txn.setCreditAmount(new BigDecimal(0));
                accountDto.setAccountBalance(accountDto.getAccountBalance().subtract(dto.getDebitAmount()));
            }

            txn.setCurrency(dto.getCurrency().name());

            txn.setTxnComments(dto.getTxnComments());
            txn.setTxnDate(OffsetDateTime.now());

            txnRepo.save(txn);
            client.updateAccount(accountDto.getAccountNumber(), accountDto);
        }

    }

    @Override
    public List<AccountTxnDto> getAllTxns(String accountNumber) {

        List<AccountTxn> list = txnRepo.findByAccountNumber(accountNumber);

        List<AccountTxnDto> listDto = new ArrayList<>();

        for (AccountTxn txn : list) {
            AccountTxnDto dto = new AccountTxnDto();
            dto.setAccountName(txn.getAccountName());
            dto.setAccountNumber(txn.getAccountNumber());
            dto.setCreditAmount(txn.getCreditAmount());
            dto.setDebitAmount(txn.getDebitAmount());
            dto.setCreditDebit(CreditDebitEnum.valueOf(txn.getCreditDebit()));
            dto.setTxnComments(txn.getTxnComments());
            dto.setCurrency(CurrencyEnum.valueOf(txn.getCurrency()));
            dto.setTxnDate(txn.getTxnDate());
            dto.setId(txn.getId());
            listDto.add(dto);
        }

        return listDto;
    }
}
