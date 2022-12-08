package vnjip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {
	public Agent findFirstByOrderByAgentNumberDesc();

	public List<Agent> findByLicenseNumber(String licenseNumber);
}
