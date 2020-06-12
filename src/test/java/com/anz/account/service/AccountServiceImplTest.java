package com.anz.account.service;

import com.anz.account.dto.AccountDto;
import com.anz.account.entity.Account;
import com.anz.account.enums.AccountTypeEnum;
import com.anz.account.enums.CurrencyEnum;
import com.anz.account.repo.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class AccountServiceImplTest {

    @InjectMocks
    private AccountService service = new AccountServiceImpl();

    @Mock
    private AccountRepository repo;


    @Test
    void create() {
        AccountDto dto = AccountDto.builder().accountBalance(new BigDecimal(0)).accountName("ABC").accountNumber("ABC123").accountType(AccountTypeEnum.Savings).currency(CurrencyEnum.AUD).build();
        Account account = new Account(1L, "ABC123", "John", "Savings", null, "AUD", new BigDecimal(100));
        when(repo.save(Mockito.any())).thenReturn(new Account(1L, "ABC123", "John", "Savings", null, "AUD", new BigDecimal(100)));
        Account savedAccount = repo.save(account);
        Assertions.assertEquals(1l, savedAccount.getId());

        Long id = service.create(dto);
        Assertions.assertEquals(1l, id);
    }

    @Test
    void getAccount() {
        Account account = new Account(1L, "ABC123", "John", "Savings", null, "AUD", new BigDecimal(100));
        when(repo.findByAccountNumber(Mockito.anyString())).thenReturn(account);

        AccountDto dto = service.getAccount("ABC123");
        Assertions.assertEquals("ABC123", dto.getAccountNumber());
    }

    @Test
    void getAllAccounts() {
        Account account = new Account(1L, "ABC123", "John", "Savings", OffsetDateTime.now(), "AUD", new BigDecimal(100));
        List<Account> list = new ArrayList<>();
        list.add(account);

        when(repo.findAll()).thenReturn(list);

        List<AccountDto> dtoList = service.getAllAccounts();
        Assertions.assertEquals("ABC123", dtoList.get(0).getAccountNumber());
    }

    @Test
    void updateAccount() {

        OffsetDateTime date = OffsetDateTime.now();

        AccountDto dto = AccountDto.builder().accountBalance(new BigDecimal(0)).accountName("ABC").accountNumber("ABC123").accountType(AccountTypeEnum.Savings).currency(CurrencyEnum.AUD).balanceDate(date).build();
        when(repo.save(Mockito.any())).thenReturn(new Account(1L, "ABC123", "John", "Savings", date, "AUD", new BigDecimal(100)));

        AccountDto saveddto = service.updateAccount(dto.getAccountNumber(), dto);
        Assertions.assertEquals(saveddto.getAccountNumber(), dto.getAccountNumber());
    }
}