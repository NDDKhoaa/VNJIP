package vnjip.entity.base;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import vnjip.entity.Agent;
import vnjip.entity.Company;

@Entity
@Table(name = "accounttype")
public class AccountType {
	@Id
	@Column(name = "account_type_short", nullable = true, length = 1)
	private String accountTypeShort;
	@Column(name = "account_type_desc", nullable = true, length = 15)
	private String accountTypeDesc;

	@OneToMany(mappedBy = "accountType")
	private List<Agent> agents;

	@OneToMany(mappedBy = "accountType")
	private List<Company> companies;

	public AccountType() {
		super();
	}

	public AccountType(String accountTypeShort, String accountTypeDesc) {
		super();
		this.accountTypeShort = accountTypeShort;
		this.accountTypeDesc = accountTypeDesc;
	}

	public String getAccountTypeShort() {
		return accountTypeShort;
	}

	public void setAccountTypeShort(String accountTypeShort) {
		this.accountTypeShort = accountTypeShort;
	}

	public String getAccountTypeDesc() {
		return accountTypeDesc;
	}

	public void setAccountTypeDesc(String accountTypeDesc) {
		this.accountTypeDesc = accountTypeDesc;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

}