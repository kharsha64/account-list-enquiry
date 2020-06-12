package com.anz.account.controller;

import com.anz.account.dto.AccountDto;
import com.anz.account.enums.AccountTypeEnum;
import com.anz.account.enums.CurrencyEnum;
import com.anz.account.service.AccountService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountsController.class)
@AutoConfigureMockMvc
class AccountsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AccountService service;

    @Test
    void createAccount() throws Exception {
        AccountDto dto = AccountDto.builder().accountBalance(new BigDecimal(0)).accountName("ABC").accountNumber("ABC123").accountType(AccountTypeEnum.Savings).currency(CurrencyEnum.AUD).build();
        when(service.create(dto)).thenReturn(1L);
        mockMvc.perform(post("/v1/api/account")
                .content(asJsonString(dto))
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
    void getAccount() throws Exception {
        AccountDto dto = AccountDto.builder().accountBalance(new BigDecimal(0)).accountName("ABC").accountNumber("ABC123").accountType(AccountTypeEnum.Savings).currency(CurrencyEnum.AUD).build();
        when(service.getAccount(Mockito.anyString())).thenReturn(dto);
        mockMvc.perform(get("/v1/api/account/{accountNumber}", "ABC123")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getAllAccounts() throws Exception {
        AccountDto dto = AccountDto.builder().accountBalance(new BigDecimal(0)).accountName("ABC").accountNumber("ABC123").accountType(AccountTypeEnum.Savings).currency(CurrencyEnum.AUD).build();
        List<AccountDto> list = new ArrayList<>();
        list.add(dto);
        when(service.getAllAccounts()).thenReturn(list);
        mockMvc.perform(get("/v1/api/account/list")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void updateAccount() throws Exception {
        AccountDto dto = AccountDto.builder().accountBalance(new BigDecimal(0)).accountName("ABC").accountNumber("ABC123").accountType(AccountTypeEnum.Savings).currency(CurrencyEnum.AUD).build();
        //doNothing().when(service).updateAccount(Mockito.anyString(), Mockito.any());
        when(service.updateAccount(Mockito.anyString(), Mockito.any())).thenReturn(dto);
        mockMvc.perform(put("/v1/api/account/{accountNumber}", "ABC123")
                    .content(asJsonString(dto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
    }
}