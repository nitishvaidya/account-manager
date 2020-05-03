package com.acmebank.accountmanager.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.acmebank.accountmanager.utils.Currency;

public class MoneyTransferDTO {

	private BigDecimal amount;

	private Currency currency;

	private String accountFrom;

	private String accountTo;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(String accountFrom) {
		this.accountFrom = accountFrom;
	}

	public String getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(String accountTo) {
		this.accountTo = accountTo;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
