package com.anz.account.controller;

import com.anz.account.dto.AccountTxnDto;
import com.anz.account.service.AccountTxnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/v1/api/account-txn")
public class AccountsTxnController {

    @Autowired
    private AccountTxnService txnService;

    @PostMapping
    public ResponseEntity createTxn(@RequestBody List<AccountTxnDto> list) {
        try {
            txnService.createTxns(list);

            return new ResponseEntity(HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Number not found", e);
        }
    }

    @GetMapping({"/{accountNumber}"})
    public ResponseEntity<List<AccountTxnDto>> getTxns(@PathVariable String accountNumber) {
        List<AccountTxnDto> list = txnService.getAllTxns(accountNumber);
        return new ResponseEntity(list, HttpStatus.OK);
    }
}
