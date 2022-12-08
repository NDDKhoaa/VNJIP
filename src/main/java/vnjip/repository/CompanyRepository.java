package vnjip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
