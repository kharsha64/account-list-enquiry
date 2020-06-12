package com.anz.account.dto;


import com.anz.account.enums.AccountTypeEnum;
import com.anz.account.enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountDto {
    private Long id;
    private String accountNumber;
    private String accountName;
    private AccountTypeEnum accountType;
    private OffsetDateTime balanceDate;
    private CurrencyEnum currency;
    private BigDecimal accountBalance;
}


