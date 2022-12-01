package vnjip.services;

import java.util.List;

import vnjip.entity.base.PolicyStatus;

public interface PolicyStatusService {

	public List<PolicyStatus> listAll();

	public void save(PolicyStatus policyStatus);

	public PolicyStatus findByShort(String policyStatusShort);

	public void delete(PolicyStatus policyStatus);

	public void deleteByShort(String policyStatusShort);

}
