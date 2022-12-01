package vnjip.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vnjip.entity.base.MaritalStatus;
import vnjip.repository.MaritalStatusRepository;
import vnjip.services.MaritalStatusService;

@Service
public class MaritalStatusServiceImpl implements MaritalStatusService {

	@Autowired
	private MaritalStatusRepository maritalRepository;

	public List<MaritalStatus> listAll() {
		return (List<MaritalStatus>) maritalRepository.findAll();
	}

	public void save(MaritalStatus marital) {
		maritalRepository.save(marital);
	}

	public MaritalStatus findByShort(String maritalShort) {
		Optional<MaritalStatus> rs = maritalRepository.findById(maritalShort);
		return rs.orElse(null);
	}

	public void delete(MaritalStatus marital) {
		maritalRepository.delete(marital);
	}

	public void deleteByShort(String maritalShort) {
		maritalRepository.deleteById(maritalShort);
	}

}
