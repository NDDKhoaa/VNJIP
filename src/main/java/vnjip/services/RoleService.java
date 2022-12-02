package vnjip.services;

import java.util.List;

import vnjip.entity.base.Role;

public interface RoleService {

	public List<Role> listAll();

	public void save(Role role);

	public Role findByNumber(long roleNumber);

	public void delete(Role role);

	public void deleteByNumber(long roleNumber);

}
