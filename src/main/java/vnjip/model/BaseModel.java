package vnjip.model;

import java.sql.Date;
import java.util.Set;

import vnjip.entity.Account;
import vnjip.entity.Agent;
import vnjip.entity.Client;
import vnjip.entity.base.Country;
import vnjip.entity.base.Gender;
import vnjip.entity.base.MaritalStatus;
import vnjip.entity.base.Role;
import vnjip.entity.enumtype.PrivilegesEnum;

public class BaseModel {

	private Account account;
	private Long accountNumber;
	private String accountUsername;
	private String accountEmail;
	private String accountPassword;
	private String accountStatus;
	private String accountPasswordConfirm;

	private Agent agent;
	private Long agentNumber;
	private String agentFirstName;
	private String agentLastName;
	private Date agentDOB;
	private String agentIdentityNumber;
	private String agentAddress;

	private Client client;
	private Long clientNumber;
	private String clientFirstName;
	private String clientLastName;
	private Date clientDOB;
	private String clientIdentityNumber;
	private String clientAddress;

	private Country country;
	private String countryShort;
	private String countryName;

	private Gender gender;
	private String genderShort;
	private String genderName;

	private MaritalStatus maritalStatusObject;
	private String maritalShort;
	private String maritalStatus;

	private Role role;
	private Long roleNumber;
	private String roleName;
	private Set<PrivilegesEnum> privileges;

	public BaseModel() {
		super();
	}

	public Set<PrivilegesEnum> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<PrivilegesEnum> privileges) {
		this.privileges = privileges;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountUsername() {
		return accountUsername;
	}

	public void setAccountUsername(String accountUsername) {
		this.accountUsername = accountUsername;
	}

	public String getAccountEmail() {
		return accountEmail;
	}

	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAccountPasswordConfirm() {
		return accountPasswordConfirm;
	}

	public void setAccountPasswordConfirm(String accountPasswordConfirm) {
		this.accountPasswordConfirm = accountPasswordConfirm;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Long getAgentNumber() {
		return agentNumber;
	}

	public void setAgentNumber(Long agentNumber) {
		this.agentNumber = agentNumber;
	}

	public String getAgentFirstName() {
		return agentFirstName;
	}

	public void setAgentFirstName(String agentFirstName) {
		this.agentFirstName = agentFirstName;
	}

	public String getAgentLastName() {
		return agentLastName;
	}

	public void setAgentLastName(String agentLastName) {
		this.agentLastName = agentLastName;
	}

	public Date getAgentDOB() {
		return agentDOB;
	}

	public void setAgentDOB(Date agentDOB) {
		this.agentDOB = agentDOB;
	}

	public String getAgentIdentityNumber() {
		return agentIdentityNumber;
	}

	public void setAgentIdentityNumber(String agentIdentityNumber) {
		this.agentIdentityNumber = agentIdentityNumber;
	}

	public String getAgentAddress() {
		return agentAddress;
	}

	public void setAgentAddress(String agentAddress) {
		this.agentAddress = agentAddress;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Long getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(Long clientNumber) {
		this.clientNumber = clientNumber;
	}

	public String getClientFirstName() {
		return clientFirstName;
	}

	public void setClientFirstName(String clientFirstName) {
		this.clientFirstName = clientFirstName;
	}

	public String getClientLastName() {
		return clientLastName;
	}

	public void setClientLastName(String clientLastName) {
		this.clientLastName = clientLastName;
	}

	public Date getClientDOB() {
		return clientDOB;
	}

	public void setClientDOB(Date clientDOB) {
		this.clientDOB = clientDOB;
	}

	public String getClientIdentityNumber() {
		return clientIdentityNumber;
	}

	public void setClientIdentityNumber(String clientIdentityNumber) {
		this.clientIdentityNumber = clientIdentityNumber;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getCountryShort() {
		return countryShort;
	}

	public void setCountryShort(String countryShort) {
		this.countryShort = countryShort;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getGenderShort() {
		return genderShort;
	}

	public void setGenderShort(String genderShort) {
		this.genderShort = genderShort;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public MaritalStatus getMaritalStatusObject() {
		return maritalStatusObject;
	}

	public void setMaritalStatusObject(MaritalStatus maritalStatusObject) {
		this.maritalStatusObject = maritalStatusObject;
	}

	public String getMaritalShort() {
		return maritalShort;
	}

	public void setMaritalShort(String maritalShort) {
		this.maritalShort = maritalShort;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getRoleNumber() {
		return roleNumber;
	}

	public void setRoleNumber(Long roleNumber) {
		this.roleNumber = roleNumber;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
