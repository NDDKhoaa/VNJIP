package vnjip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
