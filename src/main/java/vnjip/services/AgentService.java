package vnjip.services;

import java.util.List;

import vnjip.entity.Agent;

public interface AgentService {

	public List<Agent> listAll();

	public void save(Agent agent);

	public Agent findByNumber(long agentNumber);

	public void delete(Agent agent);

	public void deleteByNumber(long agentNumber);

}
