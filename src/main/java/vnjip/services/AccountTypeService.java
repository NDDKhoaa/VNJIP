package vnjip.services;

import java.util.List;

import vnjip.entity.base.AccountType;

public interface AccountTypeService {

	public List<AccountType> listAll();

	public void save(AccountType accountType);

	public AccountType findByShort(String accountTypeShort);

	public void delete(AccountType accountType);

	public void deleteByShort(String accountTypeShort);

}
