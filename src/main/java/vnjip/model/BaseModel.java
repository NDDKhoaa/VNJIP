package vnjip.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import vnjip.entity.Account;
import vnjip.entity.Agent;
import vnjip.entity.Client;
import vnjip.entity.base.AccountStatus;
import vnjip.entity.base.AccountType;
import vnjip.entity.base.BillingCurrency;
import vnjip.entity.base.Country;
import vnjip.entity.base.Gender;
import vnjip.entity.base.MaritalStatus;
import vnjip.entity.base.PolicyStatus;
import vnjip.entity.base.Role;
import vnjip.entity.enumtype.PrivilegesEnum;

public class BaseModel {

	private Account account;
	private Long accountNumber;
	private String accountUsername;
	private String accountEmail;
	private String accountPassword;
	private Set<Role> roles;

	private Agent agent;
	private Long agentNumber;
	private String agentName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date agentDOB;
	private String agentLicenseNumber;
	private String agentCompanyCode;
	private String agentCompanyName;

	private Client client;
	private Long clientNumber;
	private String clientFirstName;
	private String clientLastName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
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

	private AccountStatus accountStatus;
	private String accountStatusShort;
	private String accountStatusDesc;

	private AccountType accountType;
	private String accountTypeShort;
	private String accountTypeDesc;

	private BillingCurrency billingCurrency;
	private String currencyShort;
	private String currencyName;

	private PolicyStatus policyStatus;
	private String policyStatusShort;
	private String policyStatusDesc;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public BaseModel() {
		super();
	}

	public BaseModel(Account account2, AccountStatus accountStatus2) {
		this.accountNumber = account2.getAccountNumber();
		this.accountUsername = account2.getUsername();
		this.accountEmail = account2.getEmail();
		this.accountPassword = account2.getPassword();
		this.accountStatusShort = accountStatus2.getAccountStatusShort();
		this.accountStatusDesc = accountStatus2.getAccountStatusDesc();
	}

	public BaseModel(Agent agent2, AccountType accountType2, AccountStatus accountStatus2) {
		this.agent = agent2;
		this.agentNumber = agent2.getAgentNumber();
		this.agentName = agent2.getAgentName();
		this.agentDOB = agent2.getDateOfBirth();
		this.agentLicenseNumber = agent2.getLicenseNumber();
		this.agentCompanyCode = agent2.getCompanyCode();
		this.agentCompanyName = agent2.getCompanyName();
		this.accountStatus = accountStatus2;
		this.accountStatusShort = accountStatus2.getAccountStatusShort();
		this.accountStatusDesc = accountStatus2.getAccountStatusDesc();
		this.accountType = accountType2;
		this.accountTypeShort = accountType2.getAccountTypeShort();
		this.accountTypeDesc = accountType2.getAccountTypeDesc();
	}

	public BaseModel(Agent agent2, AccountType accountType2, AccountStatus accountStatus2, Account account2) {
		this.account = account2;
		this.accountNumber = account2.getAccountNumber();
		this.accountUsername = account2.getUsername();
		this.accountEmail = account2.getEmail();
		this.accountPassword = account2.getPassword();
		this.agent = agent2;
		this.agentNumber = agent2.getAgentNumber();
		this.agentName = agent2.getAgentName();
		this.agentDOB = agent2.getDateOfBirth();
		this.agentLicenseNumber = agent2.getLicenseNumber();
		this.agentCompanyCode = agent2.getCompanyCode();
		this.agentCompanyName = agent2.getCompanyName();
		this.accountStatus = accountStatus2;
		this.accountStatusShort = accountStatus2.getAccountStatusShort();
		this.accountStatusDesc = accountStatus2.getAccountStatusDesc();
		this.accountType = accountType2;
		this.accountTypeShort = accountType2.getAccountTypeShort();
		this.accountTypeDesc = accountType2.getAccountTypeDesc();
	}

	public BaseModel(Account account2, AccountStatus accountStatus2, Set<Role> listRole, Client client2) {
		this.account = account2;
		this.accountNumber = account2.getAccountNumber();
		this.accountUsername = account2.getUsername();
		this.accountEmail = account2.getEmail();
		this.accountPassword = account2.getPassword();
		this.client = client2;
		this.clientNumber = client2.getClientNumber();
		this.clientFirstName = client2.getFirstName();
		this.clientLastName = client2.getLastName();
		this.clientDOB = client2.getDateOfBirth();
		this.clientIdentityNumber = client2.getIdentityNumber();
		this.clientAddress = client2.getAddress();
		this.country = client2.getCountry();
		this.countryShort = client2.getCountry().getCountryShort();
		this.countryName = client2.getCountry().getCountryName();
		this.gender = client2.getGender();
		this.genderShort = client2.getGender().getGenderShort();
		this.genderName = client2.getGender().getGenderName();
		this.maritalStatusObject = client.getMaritalStatus();
		this.maritalShort = client.getMaritalStatus().getMaritalShort();
		this.maritalStatus = client.getMaritalStatus().getMaritalStatus();
		this.roles = listRole;
		this.accountStatus = account2.getAccountStatus();
		this.accountStatusShort = account2.getAccountStatus().getAccountStatusShort();
		this.accountStatusDesc = account2.getAccountStatus().getAccountStatusDesc();
	}

	public BaseModel(Account account2, AccountStatus accountStatus2, Set<Role> listRole, Agent agent2) {
		this.agent = agent2;
		this.agentNumber = agent2.getAgentNumber();
		this.agentName = agent2.getAgentName();
		this.agentDOB = agent2.getDateOfBirth();
		this.agentLicenseNumber = agent2.getLicenseNumber();
		this.agentCompanyCode = agent2.getCompanyCode();
		this.agentCompanyName = agent2.getCompanyName();
		this.accountType = agent2.getAccountType();
		this.accountTypeDesc = agent2.getAccountType().getAccountTypeDesc();
		this.accountTypeShort = agent2.getAccountType().getAccountTypeShort();
		this.account = account2;
		this.accountNumber = account2.getAccountNumber();
		this.accountUsername = account2.getUsername();
		this.accountEmail = account2.getEmail();
		this.accountPassword = account2.getPassword();
		this.roles = listRole;
		this.accountStatus = account2.getAccountStatus();
		this.accountStatusShort = account2.getAccountStatus().getAccountStatusShort();
		this.accountStatusDesc = account2.getAccountStatus().getAccountStatusDesc();
	}

	public BaseModel(Account account2, AccountStatus accountStatus2, Set<Role> listRole) {
		this.account = account2;
		this.accountNumber = account2.getAccountNumber();
		this.accountUsername = account2.getUsername();
		this.accountEmail = account2.getEmail();
		this.accountPassword = account2.getPassword();
		this.roles = listRole;
		this.accountStatus = account2.getAccountStatus();
		this.accountStatusShort = account2.getAccountStatus().getAccountStatusShort();
		this.accountStatusDesc = account2.getAccountStatus().getAccountStatusDesc();
	}

	public BaseModel(Client client2, Country country2, Gender gender2, MaritalStatus maritalStatus2) {
		this.client = client2;
		this.clientNumber = client2.getClientNumber();
		this.clientFirstName = client2.getFirstName();
		this.clientLastName = client2.getLastName();
		this.clientDOB = client2.getDateOfBirth();
		this.clientIdentityNumber = client2.getIdentityNumber();
		this.clientAddress = client2.getAddress();
		this.country = country2;
		this.countryShort = country2.getCountryShort();
		this.countryName = country2.getCountryName();
		this.gender = gender2;
		this.genderShort = gender2.getGenderShort();
		this.genderName = gender2.getGenderName();
		this.maritalStatusObject = maritalStatus2;
		this.maritalShort = maritalStatus2.getMaritalShort();
		this.maritalStatus = maritalStatus2.getMaritalStatus();
	}

	public BaseModel(Client client2, Country country2, Gender gender2, MaritalStatus maritalStatus2, Account account2) {
		this.client = client2;
		this.clientNumber = client2.getClientNumber();
		this.clientFirstName = client2.getFirstName();
		this.clientLastName = client2.getLastName();
		this.clientDOB = client2.getDateOfBirth();
		this.clientIdentityNumber = client2.getIdentityNumber();
		this.clientAddress = client2.getAddress();
		this.country = country2;
		this.countryShort = country2.getCountryShort();
		this.countryName = country2.getCountryName();
		this.gender = gender2;
		this.genderShort = gender2.getGenderShort();
		this.genderName = gender2.getGenderName();
		this.maritalStatusObject = maritalStatus2;
		this.maritalShort = maritalStatus2.getMaritalShort();
		this.maritalStatus = maritalStatus2.getMaritalStatus();
		this.account = account2;
		this.accountNumber = account2.getAccountNumber();
		this.accountUsername = account2.getUsername();
		this.accountEmail = account2.getEmail();
		this.accountPassword = account2.getPassword();
		this.accountStatus = account2.getAccountStatus();
		this.accountStatusShort = account2.getAccountStatus().getAccountStatusShort();
		this.accountStatusDesc = account2.getAccountStatus().getAccountStatusDesc();
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
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

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
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

	public BillingCurrency getBillingCurrency() {
		return billingCurrency;
	}

	public void setBillingCurrency(BillingCurrency billingCurrency) {
		this.billingCurrency = billingCurrency;
	}

	public String getCurrencyShort() {
		return currencyShort;
	}

	public void setCurrencyShort(String currencyShort) {
		this.currencyShort = currencyShort;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public PolicyStatus getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(PolicyStatus policyStatus) {
		this.policyStatus = policyStatus;
	}

	public String getPolicyStatusShort() {
		return policyStatusShort;
	}

	public void setPolicyStatusShort(String policyStatusShort) {
		this.policyStatusShort = policyStatusShort;
	}

	public String getPolicyStatusDesc() {
		return policyStatusDesc;
	}

	public void setPolicyStatusDesc(String policyStatusDesc) {
		this.policyStatusDesc = policyStatusDesc;
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

	public void setAgentDOB(Date agentDOB) {
		this.agentDOB = agentDOB;
	}

	public void setClientDOB(Date clientDOB) {
		this.clientDOB = clientDOB;
	}

	public SimpleDateFormat getFormatter() {
		return formatter;
	}

	public void setFormatter(SimpleDateFormat formatter) {
		this.formatter = formatter;
	}

	public Date getAgentDOB() {
		return agentDOB;
	}

	public Date getClientDOB() {
		return clientDOB;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentLicenseNumber() {
		return agentLicenseNumber;
	}

	public void setAgentLicenseNumber(String agentLicenseNumber) {
		this.agentLicenseNumber = agentLicenseNumber;
	}

	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}

	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}

	public String getAgentCompanyName() {
		return agentCompanyName;
	}

	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
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
