package vnjip.services;

import java.util.List;

import vnjip.entity.base.Country;

public interface CountryService {

	public List<Country> listAll();

	public void save(Country country);

	public Country findByShort(String countryShort);

	public void delete(Country country);

	public void deleteByShort(String countryShort);

}
