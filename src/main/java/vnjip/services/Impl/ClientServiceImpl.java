package vnjip.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vnjip.entity.Client;
import vnjip.repository.ClientRepository;
import vnjip.services.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository clientRepository;

	public List<Client> listAll() {
		return (List<Client>) clientRepository.findAll();
	}

	public void save(Client client) {
		clientRepository.save(client);
	}

	public Client findByNumber(long clientNumber) {
		Optional<Client> rs = clientRepository.findById(clientNumber);
		return rs.orElse(null);
	}

	public void delete(Client client) {
		clientRepository.delete(client);
	}

	public void deleteByNumber(long clientNumber) {
		clientRepository.deleteById(clientNumber);
	}

}
