package vnjip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.base.AccountStatus;

public interface AccountStatusRepository extends JpaRepository<AccountStatus, String> {
}
