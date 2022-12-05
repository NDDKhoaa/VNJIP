package vnjip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	public Client findFirstByOrderByClientNumberDesc();
}
