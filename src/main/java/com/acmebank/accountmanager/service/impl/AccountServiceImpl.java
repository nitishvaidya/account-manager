package com.acmebank.accountmanager.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acmebank.accountmanager.entity.AccountEntity;
import com.acmebank.accountmanager.entity.TransactionEntity;
import com.acmebank.accountmanager.model.AccountBalanceDTO;
import com.acmebank.accountmanager.model.MoneyTransferDTO;
import com.acmebank.accountmanager.repository.AccountRepository;
import com.acmebank.accountmanager.repository.TransactionRepository;
import com.acmebank.accountmanager.service.AccountService;
import com.acmebank.accountmanager.utils.Currency;
import com.acmebank.accountmanager.utils.CustomException;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public AccountBalanceDTO getAccountDetails(String accountNumber) throws Exception{
		AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber);
		if(accountEntity == null){
			throw new CustomException("Account does not exists");

		}
		AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
		accountBalanceDTO.setBalance(accountEntity.getBalance());
		accountBalanceDTO.setCurrency(accountEntity.getCurrency());
		accountBalanceDTO.setLastUpdatedAt(accountEntity.getLastUpdatedAt());
		return accountBalanceDTO;
		
	}

	@Override
	@Transactional
	public String postTransaction(MoneyTransferDTO moneyTransferDTO) throws Exception {
		AccountEntity accountFromEntity = accountRepository.findByAccountNumber(moneyTransferDTO.getAccountFrom());
		AccountEntity accountToEntity = accountRepository.findByAccountNumber(moneyTransferDTO.getAccountTo());

		ensureAccountIsValid(accountFromEntity, accountToEntity);
		ensureAccountHasEnoughMoney(accountFromEntity, moneyTransferDTO.getAmount(), moneyTransferDTO.getCurrency());
		
		TransactionEntity transactionEntity = new TransactionEntity();
		transactionEntity.setAccountFrom(moneyTransferDTO.getAccountFrom());
		transactionEntity.setAccountTo(moneyTransferDTO.getAccountTo());
		transactionEntity.setId(UUID.randomUUID());
		transactionEntity.setAmount(moneyTransferDTO.getAmount());
		transactionEntity.setCurrency(moneyTransferDTO.getCurrency());
		transactionEntity.setCreatedAt(LocalDateTime.now());
		transactionRepository.save(transactionEntity);
		
		accountFromEntity.setBalance(accountFromEntity.getBalance().subtract(moneyTransferDTO.getAmount()));
		accountFromEntity.setLastUpdatedAt(LocalDateTime.now());
		
		accountToEntity.setBalance(accountToEntity.getBalance().add(moneyTransferDTO.getAmount()));
		accountToEntity.setLastUpdatedAt(LocalDateTime.now());
		
		accountRepository.save(accountFromEntity);
		accountRepository.save(accountToEntity);
		return transactionEntity.getId().toString();
		
	}

	private void ensureAccountHasEnoughMoney(AccountEntity accountFromEntity, BigDecimal amount, Currency currency) throws Exception {
		if(accountFromEntity.getCurrency() != currency ||  accountFromEntity.getBalance().subtract(amount).doubleValue() <  0){
			throw new CustomException("Insufficient balance in account");
		}
	}

	private void ensureAccountIsValid(AccountEntity accountFromEntity, AccountEntity accountToEntity) throws Exception {
		
		if(accountFromEntity== null || accountToEntity == null){
			throw new CustomException("One or more Account is invalid");
		}
		
	}
	
	

}
