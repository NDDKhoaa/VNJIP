package vnjip.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vnjip.entity.Account;
import vnjip.repository.AccountRepository;
import vnjip.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public List<Account> listAll() {
		return (List<Account>) accountRepository.findAll();
	}

	public void save(Account account) {
		accountRepository.save(account);
	}

	public Account findByNumber(long accountNumber) {
		Optional<Account> rs = accountRepository.findById(accountNumber);
		return rs.orElse(null);
	}

	public void delete(Account account) {
		accountRepository.delete(account);
	}

	public void deleteByNumber(long accountNumber) {
		accountRepository.deleteById(accountNumber);
	}

}
