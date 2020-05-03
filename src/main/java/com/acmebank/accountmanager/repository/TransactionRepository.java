package com.acmebank.accountmanager.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.acmebank.accountmanager.entity.TransactionEntity;
@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, UUID> {
}
