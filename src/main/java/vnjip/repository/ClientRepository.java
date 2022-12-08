package vnjip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	public Client findFirstByOrderByClientNumberDesc();

	public List<Client> findByIdentityNumber(String id);
}
