package vnjip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.Account;

public interface UserRepository extends JpaRepository<Account, Long> {
	Account findByUsername(String username);
}
