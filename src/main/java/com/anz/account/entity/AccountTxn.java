package com.anz.account.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "Account_Transaction")
@Data
public class AccountTxn {

    @Id
    @GeneratedValue
    private Long id;

    private String accountNumber;
    private String accountName;
    private OffsetDateTime txnDate;
    private String currency;
    private String txnComments;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
    private String creditDebit;

}
