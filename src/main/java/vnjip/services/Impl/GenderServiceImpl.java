package vnjip.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vnjip.entity.base.Gender;
import vnjip.repository.GenderRepository;
import vnjip.services.GenderService;

@Service
public class GenderServiceImpl implements GenderService {

	@Autowired
	private GenderRepository genderRepository;

	public List<Gender> listAll() {
		return (List<Gender>) genderRepository.findAll();
	}

	public void save(Gender gender) {
		genderRepository.save(gender);
	}

	public Gender findByShort(String genderShort) {
		Optional<Gender> rs = genderRepository.findById(genderShort);
		return rs.orElse(null);
	}

	public void delete(Gender gender) {
		genderRepository.delete(gender);
	}

	public void deleteByShort(String genderShort) {
		genderRepository.deleteById(genderShort);
	}

}
