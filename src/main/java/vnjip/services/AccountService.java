package vnjip.services;

import java.util.List;

import vnjip.entity.Account;

public interface AccountService {

	public List<Account> listAll();

	public void save(Account account);

	public Account findByNumber(long accountNumber);

	public void delete(Account account);

	public void deleteByNumber(long accountNumber);

}
