package vnjip.entity.base;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import vnjip.entity.Agent;
import vnjip.entity.Client;

@Entity
@Table(name = "gender")
public class Gender {
	@Id
	@Column(name = "gender_short", nullable = true, length = 1)
	private String genderShort;
	@Column(name = "gender_name", nullable = true, length = 15)
	private String genderName;

	@OneToMany(mappedBy = "gender")
	private List<Agent> agents;
	@OneToMany(mappedBy = "gender")
	private List<Client> clients;

	public Gender() {
		super();
	}

	public String getGenderShort() {
		return genderShort;
	}

	public void setGenderShort(String genderShort) {
		this.genderShort = genderShort;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

}