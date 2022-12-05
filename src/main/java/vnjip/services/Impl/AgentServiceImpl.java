package vnjip.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vnjip.entity.Agent;
import vnjip.repository.AgentRepository;
import vnjip.services.AgentService;

@Service
public class AgentServiceImpl implements AgentService {

	@Autowired
	private AgentRepository agentRepository;

	public List<Agent> listAll() {
		return (List<Agent>) agentRepository.findAll();
	}

	public void save(Agent agent) {
		agentRepository.save(agent);
	}

	public Agent findByNumber(long agentNumber) {
		Optional<Agent> rs = agentRepository.findById(agentNumber);
		return rs.orElse(null);
	}

	public void delete(Agent agent) {
		agentRepository.delete(agent);
	}

	public void deleteByNumber(long agentNumber) {
		agentRepository.deleteById(agentNumber);
	}

	public Agent findTopAgentNumber() {
		return agentRepository.findFirstByOrderByAgentNumberDesc();
	}

}
