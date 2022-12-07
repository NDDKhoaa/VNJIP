package vnjip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
	public Policy findFirstByOrderByPolicyNumberDesc();
	public List<Policy> findByEngineNo(String engineNo);
	public List<Policy> findByChassisNo(String chassisNo);
}
