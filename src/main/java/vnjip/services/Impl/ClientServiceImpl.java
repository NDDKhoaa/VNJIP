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

	public Client findTopClientNumber() {
		return clientRepository.findFirstByOrderByClientNumberDesc();
	}

	public boolean findByIdentityNumber(String id) {
		List<Client> list = clientRepository.findByIdentityNumber(id);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean findByIdentityNumberModify(String id) {
		List<Client> list = clientRepository.findByIdentityNumber(id);
		if (list.size() > 1) {
			return true;
		} else {
			return false;
		}
	}
}
