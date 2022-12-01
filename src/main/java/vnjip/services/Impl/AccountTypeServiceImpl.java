package vnjip.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vnjip.entity.base.AccountType;
import vnjip.repository.AccountTypeRepository;
import vnjip.services.AccountTypeService;

@Service
public class AccountTypeServiceImpl implements AccountTypeService {

	@Autowired
	private AccountTypeRepository accountTypeRepository;

	public List<AccountType> listAll() {
		return (List<AccountType>) accountTypeRepository.findAll();
	}

	public void save(AccountType accountType) {
		accountTypeRepository.save(accountType);
	}

	public AccountType findByShort(String accountTypeShort) {
		Optional<AccountType> rs = accountTypeRepository.findById(accountTypeShort);
		return rs.orElse(null);
	}

	public void delete(AccountType accountType) {
		accountTypeRepository.delete(accountType);
	}

	public void deleteByShort(String accountTypeShort) {
		accountTypeRepository.deleteById(accountTypeShort);
	}

}
