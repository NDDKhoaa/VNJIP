package vnjip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.base.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
