package vnjip.configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import vnjip.entity.Account;
import vnjip.entity.base.AccountStatus;
import vnjip.entity.base.Role;
import vnjip.entity.enumtype.PrivilegesEnum;
import vnjip.repository.AccountRepository;
import vnjip.repository.RoleRepository;
import vnjip.services.Impl.AccountStatusServiceImpl;
import vnjip.services.Impl.AgentServiceImpl;
import vnjip.services.Impl.ClientServiceImpl;

@Component
public class SpringBootInitialData implements ApplicationRunner {

	@Autowired
	private AccountRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private AccountStatusServiceImpl accountStatusServiceImpl;
	@Autowired
	private ClientServiceImpl clientServiceImpl;
	@Autowired
	private AgentServiceImpl agentServiceImpl;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<Role> roles = roleRepository.findAll();
		if (roles.isEmpty()) {
			Set<PrivilegesEnum> privilegesEnumSet = new HashSet<>();
			privilegesEnumSet.add(PrivilegesEnum.ROLE_ADMIN);
			Role role = new Role("ROLE_ADMIN", privilegesEnumSet);
			roleRepository.save(role);
			roles = roleRepository.findAll();
			String pwdEncrypt = bCryptPasswordEncoder.encode("123456");
			AccountStatus accountStatus = accountStatusServiceImpl.findByShort("A");
			Account account = new Account("admin", "admin@gmail.com", pwdEncrypt, new HashSet<>(roles), accountStatus);
			userRepository.save(account);
			String sClientDOB = "20221203";
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			Date clientDOB = formatter1.parse(sClientDOB);
		}
	}
}
