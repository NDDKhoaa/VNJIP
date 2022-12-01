package vnjip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.base.AccountType;

public interface AccountTypeRepository extends JpaRepository<AccountType, String> {
}
