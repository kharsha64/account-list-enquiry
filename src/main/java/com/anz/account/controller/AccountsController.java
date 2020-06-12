package com.anz.account.controller;

import com.anz.account.dto.AccountDto;
import com.anz.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/v1/api/account")
public class AccountsController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity createAccount(@RequestBody AccountDto dto) {
        Long id = accountService.create(dto);
        return new ResponseEntity(id, HttpStatus.CREATED);
    }

    @GetMapping({"/{accountNumber}"})
    public ResponseEntity<AccountDto> getAccount(@PathVariable String accountNumber) {
        try {
            AccountDto dto = accountService.getAccount(accountNumber);
            return new ResponseEntity(dto, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Number not found", e);
        }
    }

    @GetMapping({"/list"})
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accountDtoList = accountService.getAllAccounts();
        return new ResponseEntity(accountDtoList, HttpStatus.OK);
    }

    @PutMapping({"/{accountNumber}"})
    public ResponseEntity updateAccount(@PathVariable("accountNumber") String accountNumber, @RequestBody AccountDto dto) {
        accountService.updateAccount(accountNumber, dto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
