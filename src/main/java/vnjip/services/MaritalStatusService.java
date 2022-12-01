package vnjip.services;

import java.util.List;

import vnjip.entity.base.MaritalStatus;

public interface MaritalStatusService {

	public List<MaritalStatus> listAll();

	public void save(MaritalStatus maritalStatus);

	public MaritalStatus findByShort(String maritalShort);

	public void delete(MaritalStatus maritalStatus);

	public void deleteByShort(String maritalShort);

}
