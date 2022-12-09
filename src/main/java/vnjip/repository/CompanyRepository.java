package vnjip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	public List<Company> findByCompanyCodeAndCompanyName(long companyCode, String companyName);
}
