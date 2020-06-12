package com.anz.account.controller;

import com.anz.account.dto.AccountTxnDto;
import com.anz.account.enums.CreditDebitEnum;
import com.anz.account.enums.CurrencyEnum;
import com.anz.account.service.AccountTxnService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountsTxnController.class)
@AutoConfigureMockMvc
class AccountsTxnControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AccountTxnService service;

    @Test
    void createTxn() throws Exception {
        AccountTxnDto dto = AccountTxnDto.builder().accountName("ABC").accountNumber("ABC123").currency(CurrencyEnum.AUD).creditDebit(CreditDebitEnum.CREDIT).creditAmount(new BigDecimal(100)).build();
        List<AccountTxnDto> list = new ArrayList<>();
        list.add(dto);

        doNothing().when(service).createTxns(Mockito.anyList());

        mockMvc.perform(post("/v1/api/account-txn")
                        .content(asJsonString(list))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getTxns() throws Exception {
        AccountTxnDto dto = AccountTxnDto.builder().accountName("ABC").accountNumber("ABC123").currency(CurrencyEnum.AUD).creditDebit(CreditDebitEnum.CREDIT).creditAmount(new BigDecimal(100)).build();
        List<AccountTxnDto> list = new ArrayList<>();
        list.add(dto);

        when(service.getAllTxns(Mockito.anyString())).thenReturn(list);

        mockMvc.perform(get("/v1/api/account-txn/{accountNumber}", "ABC123")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

    }
}