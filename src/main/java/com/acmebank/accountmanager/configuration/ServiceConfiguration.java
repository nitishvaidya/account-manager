package com.acmebank.accountmanager.configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acmebank.accountmanager.entity.AccountEntity;
import com.acmebank.accountmanager.repository.AccountRepository;
import com.acmebank.accountmanager.utils.Currency;

@Service
public class ServiceConfiguration implements InitializingBean {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public void afterPropertiesSet() throws Exception {
		addAccountIfNotExists("12345678");
		addAccountIfNotExists("88888888");

	}

	private void addAccountIfNotExists(String accountNumber) {

		AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber);

		if (accountEntity == null) {
			accountEntity = new AccountEntity();
			accountEntity.setAccountNumber(accountNumber);
			accountEntity.setBalance(BigDecimal.valueOf(1000000));
			accountEntity.setCurrency(Currency.HKD);
			accountEntity.setId(UUID.randomUUID());
			accountEntity.setLastUpdatedAt(LocalDateTime.now());
			accountRepository.save(accountEntity);
		}
	}

}
