package vnjip.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vnjip.entity.base.ErrorPf;
import vnjip.repository.ErrorPfRepository;
import vnjip.services.ErrorPfService;


@Service
public class ErrorPfImpl implements ErrorPfService {

	@Autowired
	private ErrorPfRepository errorPfRepository;

	public List<ErrorPf> listAll() {
		return (List<ErrorPf>) errorPfRepository.findAll();
	}

	public void save(ErrorPf errorPf) {
		errorPfRepository.save(errorPf);
	}

	public ErrorPf findByShort(String errorCode) {
		Optional<ErrorPf> rs = errorPfRepository.findById(errorCode);
		return rs.orElse(null);
	}

	public void delete(ErrorPf errorPf) {
		errorPfRepository.delete(errorPf);
	}

	public void deleteByShort(String errorCode) {
		errorPfRepository.deleteById(errorCode);
	}

}
