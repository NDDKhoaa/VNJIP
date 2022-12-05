package vnjip.entity.base;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import vnjip.entity.Account;
import vnjip.entity.Agent;

@Entity
@Table(name = "accountstatus")
public class AccountStatus {
	@Id
	@Column(name = "account_status_short", nullable = true, length = 1)
	private String accountStatusShort;
	@Column(name = "account_status_desc", nullable = true, length = 15)
	private String accountStatusDesc;

	@OneToMany(mappedBy = "accountStatus")
	private List<Agent> agents;

	@OneToMany(mappedBy = "accountStatus")
	private List<Account> accounts;

	public AccountStatus() {
		super();
	}

	public AccountStatus(String accountStatusShort, String accountStatusDesc) {
		super();
		this.accountStatusShort = accountStatusShort;
		this.accountStatusDesc = accountStatusDesc;
	}

	public String getAccountStatusShort() {
		return accountStatusShort;
	}

	public void setAccountStatusShort(String accountStatusShort) {
		this.accountStatusShort = accountStatusShort;
	}

	public String getAccountStatusDesc() {
		return accountStatusDesc;
	}

	public void setAccountStatusDesc(String accountStatusDesc) {
		this.accountStatusDesc = accountStatusDesc;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

}