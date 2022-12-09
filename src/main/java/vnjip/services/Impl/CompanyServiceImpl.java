package vnjip.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vnjip.entity.Company;
import vnjip.repository.CompanyRepository;
import vnjip.services.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	public List<Company> listAll() {
		return (List<Company>) companyRepository.findAll();
	}

	public void save(Company company) {
		companyRepository.save(company);
	}

	public Company findByNumber(long companyCode) {
		Optional<Company> rs = companyRepository.findById(companyCode);
		return rs.orElse(null);
	}

	public void delete(Company company) {
		companyRepository.delete(company);
	}

	public void deleteByNumber(long companyCode) {
		companyRepository.deleteById(companyCode);
	}

	public List<Company> findByCompanyCodeAndCompanyName(long companyCode, String companyName) {
		return companyRepository.findByCompanyCodeAndCompanyName(companyCode, companyName);
	}

	public boolean checkFindCompany(List<Company> list, String accountType) {
		int count = 0;
		for (Company company : list) {
			if (company.getAccountType().getAccountTypeShort().equals(accountType)) {
				count++;
			}
		}
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkFindCompanyModify(List<Company> list, String accountType) {
		int count = 0;
		for (Company company : list) {
			if (company.getAccountType().getAccountTypeShort().equals(accountType)) {
				count++;
			}
		}
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

}
