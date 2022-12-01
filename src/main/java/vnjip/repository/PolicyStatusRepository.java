package vnjip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.base.PolicyStatus;

public interface PolicyStatusRepository extends JpaRepository<PolicyStatus, String> {
}
