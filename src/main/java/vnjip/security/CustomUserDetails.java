package vnjip.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import vnjip.entity.Account;
import vnjip.entity.base.Role;
import vnjip.entity.enumtype.PrivilegesEnum;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	Account account;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = account.getRoles();
		Set<PrivilegesEnum> privilegesEnums = new HashSet<>();
		for (Role role : roles) {
			privilegesEnums.addAll(role.getPrivileges());
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (PrivilegesEnum privilegesEnum : privilegesEnums) {
			authorities.add(new SimpleGrantedAuthority(privilegesEnum.toString()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Account getAccount() {
		return account;
	}

	public String getFullName() {
		String lastName = "";
		if (account.getUsername() != null) {
			lastName = account.getUsername();
		}
		return lastName;
	}
}
