package vnjip.services;

import java.util.List;

import vnjip.entity.Policy;

public interface PolicyService {

	public List<Policy> listAll();

	public void save(Policy policy);

	public Policy findByNumber(long policyNumber);

	public void delete(Policy policy);

	public void deleteByNumber(long policyNumber);

}
