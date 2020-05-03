package com.acmebank.accountmanager.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.acmebank.accountmanager.model.MoneyTransferDTO;

public interface AccountApi {
	
	@GetMapping("v1/accounts/{accountNumber}")
	@CrossOrigin
	public ResponseEntity<?> getAccountDetails(@PathVariable("accountNumber") String accountNumber) throws Exception;

	
	@PostMapping("v1/transactions")
	@CrossOrigin
	public ResponseEntity<?> transferMoney(@RequestBody MoneyTransferDTO moneyTransferDTO) throws Exception;

}
