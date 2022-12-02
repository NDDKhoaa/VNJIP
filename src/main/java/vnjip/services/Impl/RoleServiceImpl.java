package vnjip.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vnjip.entity.base.Role;
import vnjip.repository.RoleRepository;
import vnjip.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public List<Role> listAll() {
		return (List<Role>) roleRepository.findAll();
	}

	public void save(Role role) {
		roleRepository.save(role);
	}

	public Role findByNumber(long roleNumber) {
		Optional<Role> rs = roleRepository.findById(roleNumber);
		return rs.orElse(null);
	}

	public void delete(Role role) {
		roleRepository.delete(role);
	}

	public void deleteByNumber(long roleNumber) {
		roleRepository.deleteById(roleNumber);
	}

}
