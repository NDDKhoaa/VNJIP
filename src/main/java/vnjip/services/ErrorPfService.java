package vnjip.services;

import java.util.List;

import vnjip.entity.base.ErrorPf;

public interface ErrorPfService {
	public List<ErrorPf> listAll();

	public void save(ErrorPf errorPf);

	public ErrorPf findByShort(String errorCode);

	public void delete(ErrorPf gender);

	public void deleteByShort(String errorCode);
}
