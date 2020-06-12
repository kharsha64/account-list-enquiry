package com.anz.account.client;

import com.anz.account.dto.AccountDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AccountClient {


    private RestTemplate restTemplate;

    public AccountClient (RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public AccountDto getAccount(String accountNumber) {
        return restTemplate.getForObject("http://localhost:8080/v1/api/account/" + accountNumber, AccountDto.class);
    }

    public void updateAccount(String accountNumber, AccountDto dto) {
        restTemplate.put("http://localhost:8080/v1/api/account/" + accountNumber, dto);
    }
}
