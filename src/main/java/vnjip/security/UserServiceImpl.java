package vnjip.security;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import vnjip.entity.Account;
import vnjip.repository.AccountRepository;
import vnjip.repository.RoleRepository;

@Service
public class UserServiceImpl {
	@Autowired
	private AccountRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void save(Account user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(new HashSet<>(roleRepository.findAll()));
		userRepository.save(user);
	}

	public Account findByUsername(String account) {
		return userRepository.findByUsername(account);
	}
}