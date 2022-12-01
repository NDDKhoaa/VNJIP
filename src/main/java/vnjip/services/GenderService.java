package vnjip.services;

import java.util.List;

import vnjip.entity.base.Gender;

public interface GenderService {

	public List<Gender> listAll();

	public void save(Gender gender);

	public Gender findByShort(String genderShort);

	public void delete(Gender gender);

	public void deleteByShort(String genderShort);

}
