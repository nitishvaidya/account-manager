package com.acmebank.accountmanager.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.acmebank.accountmanager.utils.Currency;

public class AccountBalanceDTO {
	
	private BigDecimal balance;
	
	private Currency currency;
	
	private LocalDateTime lastUpdatedAt;

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public LocalDateTime getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}
	
	
	
	

}
