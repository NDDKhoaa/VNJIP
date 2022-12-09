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

	@ManyToOne
	@JoinColumn(name = "account_status_short", nullable = true)
	private AccountStatus accountStatus;

	@ManyToOne
	@JoinColumn(name = "account_type_short", nullable = true)
	private AccountType accountType;

	@OneToOne
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	public Agent() {
		super();
	}

	public Agent(BaseModel model, Company company2, AccountType accountType2, AccountStatus accountStatus2) {
		this.agentName = model.getAgentName();
		this.dateOfBirth = model.getAgentDOB();
		this.licenseNumber = model.getAgentLicenseNumber();
		this.accountStatus = accountStatus2;
		this.accountType = accountType2;
		this.company = company2;
	}

	public Agent(Agent updateAgent) {
		this.agentName = updateAgent.getAgentName();
		this.dateOfBirth = updateAgent.getDateOfBirth();
		this.licenseNumber = updateAgent.getLicenseNumber();
		this.accountStatus = getAccountStatus();
	}

	public Agent(Agent updateAgent, Company company, AccountStatus accountStatus2, AccountType accountType2) {
		this.agentName = updateAgent.getAgentName();
		this.dateOfBirth = updateAgent.getDateOfBirth();
		this.licenseNumber = updateAgent.getLicenseNumber();
		this.accountStatus = accountStatus2;
		this.accountType = accountType2;
		this.company = company;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}