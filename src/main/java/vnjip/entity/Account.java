package vnjip.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import vnjip.entity.base.AccountStatus;
import vnjip.entity.base.Role;

@Entity
@Table(name = "account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_number", nullable = false, unique = true)
	private Long accountNumber;
	@Column(name = "username", nullable = true, unique = true, length = 30)
	private String username;
	@Column(name = "email", nullable = true, unique = true, length = 30)
	private String email;
	@Column(name = "password", nullable = true, length = 30)
	private String password;

	@Transient
	private String passwordConfirm;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "accounts_roles", joinColumns = @JoinColumn(name = "account_number", referencedColumnName = "account_number"), inverseJoinColumns = @JoinColumn(name = "role_number", referencedColumnName = "role_number", table = "role"))
	private Set<Role> roles;

	@ManyToOne
	@JoinColumn(name = "account_status_short", nullable = true)
	private AccountStatus accountStatus;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "agent_number", nullable = true)
	private Agent agent;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "client_number", nullable = true)
	private Client client;

	public Account() {
		super();
	}

	public Account(String username, String email, String password, Set<Role> roles, AccountStatus accountStatus) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.accountStatus = accountStatus;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

}