package com.acmebank.accountmanager.service;

import com.acmebank.accountmanager.model.AccountBalanceDTO;
import com.acmebank.accountmanager.model.MoneyTransferDTO;
import com.acmebank.accountmanager.utils.CustomException;

public interface AccountService {
	public AccountBalanceDTO getAccountDetails(String accountNumber) throws Exception;

	public String postTransaction(MoneyTransferDTO moneyTransferDTO) throws Exception;
}
