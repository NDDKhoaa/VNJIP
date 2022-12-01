package vnjip.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vnjip.entity.base.PolicyStatus;
import vnjip.repository.PolicyStatusRepository;
import vnjip.services.PolicyStatusService;

@Service
public class PolicyStatusServiceImpl implements PolicyStatusService {

	@Autowired
	private PolicyStatusRepository policyStatusRepository;

	public List<PolicyStatus> listAll() {
		return (List<PolicyStatus>) policyStatusRepository.findAll();
	}

	public void save(PolicyStatus policyStatus) {
		policyStatusRepository.save(policyStatus);
	}

	public PolicyStatus findByShort(String policyStatusShort) {
		Optional<PolicyStatus> rs = policyStatusRepository.findById(policyStatusShort);
		return rs.orElse(null);
	}

	public void delete(PolicyStatus policyStatus) {
		policyStatusRepository.delete(policyStatus);
	}

	public void deleteByShort(String policyStatusShort) {
		policyStatusRepository.deleteById(policyStatusShort);
	}

}
