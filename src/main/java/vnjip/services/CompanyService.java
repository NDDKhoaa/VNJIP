package vnjip.services;

import java.util.List;

import vnjip.entity.Company;

public interface CompanyService {

	public List<Company> listAll();

	public void save(Company company);

	public Company findByNumber(long companyCode);

	public void delete(Company company);

	public void deleteByNumber(long companyCode);

}
