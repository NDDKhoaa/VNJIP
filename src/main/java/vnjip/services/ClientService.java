package vnjip.services;

import java.util.List;

import vnjip.entity.Client;

public interface ClientService {

	public List<Client> listAll();

	public void save(Client client);

	public Client findByNumber(long clientNumber);

	public void delete(Client client);

	public void deleteByNumber(long clientNumber);

	public Client findTopClientNumber();
}
