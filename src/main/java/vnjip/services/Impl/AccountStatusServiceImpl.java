package vnjip.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vnjip.entity.base.AccountStatus;
import vnjip.repository.AccountStatusRepository;
import vnjip.services.AccountStatusService;

@Service
public class AccountStatusServiceImpl implements AccountStatusService {

	@Autowired
	private AccountStatusRepository accountStatusRepository;

	public List<AccountStatus> listAll() {
		return (List<AccountStatus>) accountStatusRepository.findAll();
	}

	public void save(AccountStatus accountStatus) {
		accountStatusRepository.save(accountStatus);
	}

	public AccountStatus findByShort(String accountStatusShort) {
		Optional<AccountStatus> rs = accountStatusRepository.findById(accountStatusShort);
		return rs.orElse(null);
	}

	public void delete(AccountStatus accountStatus) {
		accountStatusRepository.delete(accountStatus);
	}

	public void deleteByShort(String accountStatusShort) {
		accountStatusRepository.deleteById(accountStatusShort);
	}

}
