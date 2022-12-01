package vnjip.entity.base;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import vnjip.entity.Account;

@Entity
@Table(name = "role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_number", nullable = false, unique = true)
	private Long roleNumber;
	@Column(name = "role_name", nullable = true)
	private String roleName;

	@ManyToMany
	private List<Account> accounts;

	public Role() {
		super();
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

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

}