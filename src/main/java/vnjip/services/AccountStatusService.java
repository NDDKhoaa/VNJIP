package vnjip.services;

import java.util.List;

import vnjip.entity.base.AccountStatus;

public interface AccountStatusService {

	public List<AccountStatus> listAll();

	public void save(AccountStatus accountStatus);

	public AccountStatus findByShort(String accountStatusShort);

	public void delete(AccountStatus accountStatus);

	public void deleteByShort(String accountStatusShort);

}
