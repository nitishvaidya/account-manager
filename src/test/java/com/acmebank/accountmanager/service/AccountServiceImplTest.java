package com.acmebank.accountmanager.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.MockitoAnnotations;

import com.acmebank.accountmanager.entity.AccountEntity;
import com.acmebank.accountmanager.model.AccountBalanceDTO;
import com.acmebank.accountmanager.model.MoneyTransferDTO;
import com.acmebank.accountmanager.repository.AccountRepository;
import com.acmebank.accountmanager.repository.TransactionRepository;
import com.acmebank.accountmanager.service.impl.AccountServiceImpl;
import com.acmebank.accountmanager.utils.Currency;
import com.acmebank.accountmanager.utils.CustomException;

public class AccountServiceImplTest {

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private AccountServiceImpl accountServiceImpl;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSuccessfulGetBalance() throws Exception {
		Mockito.when(accountRepository.findByAccountNumber("12345678"))
				.thenReturn(geAccountEntity("12345678", 1000000));
		AccountBalanceDTO accountBalanceDTO = accountServiceImpl.getAccountDetails("12345678");
		Assertions.assertEquals(Currency.HKD, accountBalanceDTO.getCurrency());
		Assertions.assertEquals(1000000, accountBalanceDTO.getBalance().intValue());
	}

	@Test
	public void testFailedToGetBalance() {
		Mockito.when(accountRepository.findByAccountNumber("12345678")).thenReturn(null);
		Assertions.assertThrows(CustomException.class, () -> {
			accountServiceImpl.getAccountDetails("12345678");
		});
	}

	@Test
	public void testFailedToTransferMoneyForInvalidAccount() {
		Mockito.when(accountRepository.findByAccountNumber("12345678")).thenReturn(null);
		Assertions.assertThrows(CustomException.class, () -> {
			accountServiceImpl.postTransaction(geMoneyTransferDTO());
		});
	}

	@Test
	public void testFailedToTransferMoneyForInsufficientBalance() {
		Mockito.when(accountRepository.findByAccountNumber("12345678")).thenReturn(geAccountEntity("12345678", 0));
		Mockito.when(accountRepository.findByAccountNumber("88888888")).thenReturn(geAccountEntity("88888888", 10000));
		Assertions.assertThrows(CustomException.class, () -> {
			accountServiceImpl.postTransaction(geMoneyTransferDTO());
		});
	}

	@Test
	public void testSuccessfulMoneyTransfer() throws Exception {
		Mockito.when(accountRepository.findByAccountNumber("12345678")).thenReturn(geAccountEntity("12345678", 10000));
		Mockito.when(accountRepository.findByAccountNumber("88888888")).thenReturn(geAccountEntity("88888888", 0));
		ArgumentCaptor<AccountEntity> accountCaptor = ArgumentCaptor.forClass(AccountEntity.class);
		String transactionId = accountServiceImpl.postTransaction(geMoneyTransferDTO());
		Mockito.verify(accountRepository, Mockito.times(2)).save(accountCaptor.capture());

		accountCaptor.getAllValues().forEach(account ->{
			if(account.getAccountNumber().equals("12345678")){
				Assertions.assertEquals(9000, account.getBalance().doubleValue());
			}
			if(account.getAccountNumber().equals("88888888")){
				Assertions.assertEquals(1000, account.getBalance().doubleValue());
			}
		});
		
	}

	
	private MoneyTransferDTO geMoneyTransferDTO() {
		MoneyTransferDTO moneyTransferDTO = new MoneyTransferDTO();
		moneyTransferDTO.setAccountFrom("12345678");
		moneyTransferDTO.setAccountTo("88888888");
		moneyTransferDTO.setAmount(BigDecimal.valueOf(1000));
		moneyTransferDTO.setCurrency(Currency.HKD);
		return moneyTransferDTO;
	}

	private AccountEntity geAccountEntity(String accountNumber, int balance) {
		AccountEntity accountEntity = new AccountEntity();
		accountEntity.setAccountNumber(accountNumber);
		accountEntity.setBalance(BigDecimal.valueOf(balance));
		accountEntity.setCurrency(Currency.HKD);
		accountEntity.setId(UUID.randomUUID());
		accountEntity.setLastUpdatedAt(LocalDateTime.now());
		return accountEntity;
	}

}
