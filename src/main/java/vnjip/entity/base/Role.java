package vnjip.entity.base;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import vnjip.entity.Account;
import vnjip.entity.enumtype.PrivilegesEnum;
import vnjip.model.BaseModel;

@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_number", unique = true, nullable = false)
	private long roleNumber;

	@Column(name = "role_name", unique = true)
	private String roleName;

	@ElementCollection(targetClass = PrivilegesEnum.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@Column(name = "privileges")
	private Set<PrivilegesEnum> privileges;

	@ManyToMany(mappedBy = "roles")
	private Set<Account> accounts;

	public Role() {
	}

	public Role(long roleNumber, String roleName) {
		this.roleNumber = roleNumber;
		this.roleName = roleName;
	}

	public Role(String roleName, Set<PrivilegesEnum> privileges) {
		this.roleName = roleName;
		this.privileges = privileges;
	}

	public Role(BaseModel roleModel) {
		this.roleNumber = roleModel.getRoleNumber();
		this.roleName = roleModel.getRoleName();
		this.privileges = roleModel.getPrivileges();
	}

	public long getRoleNumber() {
		return roleNumber;
	}

	public void setRoleNumber(long roleNumber) {
		this.roleNumber = roleNumber;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<PrivilegesEnum> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<PrivilegesEnum> privileges) {
		this.privileges = privileges;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

}
