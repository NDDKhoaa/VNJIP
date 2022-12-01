package vnjip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {
}
