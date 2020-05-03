package com.acmebank.accountmanager.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.acmebank.accountmanager.api.AccountApi;
import com.acmebank.accountmanager.model.AccountBalanceDTO;
import com.acmebank.accountmanager.model.MoneyTransferDTO;
import com.acmebank.accountmanager.service.AccountService;
import com.acmebank.accountmanager.utils.CustomException;

@Controller
public class AccountApiController implements AccountApi {

	@Autowired
	private AccountService accountService;

	@Override
	public ResponseEntity<?> getAccountDetails(String accountNumber) throws Exception {
		AccountBalanceDTO accountBalanceDTO = accountService.getAccountDetails(accountNumber);
		return ResponseEntity.ok(accountBalanceDTO);
	}

	@Override
	public ResponseEntity<?> transferMoney(MoneyTransferDTO moneyTransferDTO) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(accountService.postTransaction(moneyTransferDTO));
	}

}
