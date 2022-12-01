package vnjip.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vnjip.entity.base.Country;
import vnjip.repository.CountryRepository;
import vnjip.services.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository countryRepository;

	public List<Country> listAll() {
		return (List<Country>) countryRepository.findAll();
	}

	public void save(Country country) {
		countryRepository.save(country);
	}

	public Country findByShort(String countryShort) {
		Optional<Country> rs = countryRepository.findById(countryShort);
		return rs.orElse(null);
	}

	public void delete(Country country) {
		countryRepository.delete(country);
	}

	public void deleteByShort(String countryShort) {
		countryRepository.deleteById(countryShort);
	}

}
