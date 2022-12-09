package vnjip.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import vnjip.entity.base.AccountType;

@Entity
@Table(name = "company")
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id", nullable = false, unique = true)
	private long companyId;

	@Column(name = "company_code", nullable = true, length = 8)
	private long companyCode;

	@Column(name = "company_name", nullable = true, length = 200)
	private String companyName;

	@ManyToOne
	@JoinColumn(name = "account_type_short", nullable = true)
	private AccountType accountType;

	public Company() {
		super();
	}

	public Company(long companyCode, String companyName, AccountType accountType) {
		super();
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.accountType = accountType;
	}

	public long getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(long companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

}