package com.anz.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String accountNumber;
    private String accountName;
    private String type;
    private OffsetDateTime balanceDate;
    private String currency;
    private BigDecimal accountBalance;
}
