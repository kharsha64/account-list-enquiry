package com.anz.account.dto;

import com.anz.account.enums.CreditDebitEnum;
import com.anz.account.enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountTxnDto {
    private Long id;
    private String accountNumber;
    private String accountName;
    private OffsetDateTime txnDate;
    private CurrencyEnum currency;
    private String txnComments;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
    private CreditDebitEnum creditDebit;
}


