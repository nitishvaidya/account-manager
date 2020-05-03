package com.acmebank.accountmanager.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.acmebank.accountmanager.entity.AccountEntity;
@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, UUID> {

	AccountEntity findByAccountNumber(String accountNumber);
}
