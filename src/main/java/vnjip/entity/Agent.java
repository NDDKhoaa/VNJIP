package vnjip.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import vnjip.entity.base.AccountStatus;
import vnjip.entity.base.AccountType;
import vnjip.model.BaseModel;

@Entity
@Table(name = "agent")
public class Agent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "agent_number", nullable = false, unique = true)
	private Long agentNumber;
	@Column(name = "agent_name", nullable = true, length = 60)
	private String agentName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth", nullable = true)
	private Date dateOfBirth;
	@Column(name = "license_number", nullable = true, length = 20)
	private String licenseNumber;
	@Column(name = "company_code", nullable = true, length = 8)
	private String companyCode;
	@Column(name = "company_name", nullable = true, length = 60)
	private String companyName;

	@ManyToOne
	@JoinColumn(name = "account_status_short", nullable = true)
	private AccountStatus accountStatus;

	@ManyToOne
	@JoinColumn(name = "account_type_short", nullable = true)
	private AccountType accountType;

	@OneToOne
	@JoinColumn(name = "account_number", nullable = true)
	private Account account;

	public Agent() {
		super();
	}

	public Agent(BaseModel model, AccountType accountType2, AccountStatus accountStatus2) {
		this.agentName = model.getAgentName();
		this.dateOfBirth = model.getAgentDOB();
		this.licenseNumber = model.getAgentLicenseNumber();
		this.companyCode = model.getAgentCompanyCode();
		this.companyName = model.getAgentCompanyName();
		this.accountStatus = accountStatus2;
		this.accountType = accountType2;
	}

	public Agent(Agent updateAgent) {
		this.agentName = updateAgent.getAgentName();
		this.dateOfBirth = updateAgent.getDateOfBirth();
		this.licenseNumber = updateAgent.getLicenseNumber();
		this.companyCode = updateAgent.getCompanyCode();
		this.companyName = updateAgent.getCompanyName();
		this.accountStatus = getAccountStatus();
	}

	public Agent(Agent updateAgent, AccountStatus accountStatus2, AccountType accountType2) {
		this.agentName = updateAgent.getAgentName();
		this.dateOfBirth = updateAgent.getDateOfBirth();
		this.licenseNumber = updateAgent.getLicenseNumber();
		this.companyCode = updateAgent.getCompanyCode();
		this.companyName = updateAgent.getCompanyName();
		this.accountStatus = accountStatus2;
		this.accountType = accountType2;
	}

	public Long getAgentNumber() {
		return agentNumber;
	}

	public void setAgentNumber(Long agentNumber) {
		this.agentNumber = agentNumber;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}