package vnjip.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vnjip.entity.Policy;
import vnjip.repository.PolicyRepository;
import vnjip.services.PolicyService;

@Service
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	private PolicyRepository policyRepository;

	public List<Policy> listAll() {
		return (List<Policy>) policyRepository.findAll();
	}

	public void save(Policy policy) {
		policyRepository.save(policy);
	}

	public Policy findByNumber(long policyNumber) {
		Optional<Policy> rs = policyRepository.findById(policyNumber);
		return rs.orElse(null);
	}

	public void delete(Policy policy) {
		policyRepository.delete(policy);
	}

	public void deleteByNumber(long policyNumber) {
		policyRepository.deleteById(policyNumber);
	}

}
