package vnjip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.base.MaritalStatus;

public interface MaritalStatusRepository extends JpaRepository<MaritalStatus, String> {
}
