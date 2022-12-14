package vnjip.entity.base;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import vnjip.entity.Client;

@Entity
@Table(name = "maritalstatus")
public class MaritalStatus {
	@Id
	@Column(name = "marital_short", nullable = false, unique = true, length = 1)
	private String maritalShort;
	@Column(name = "marital_status", nullable = true, length = 15)
	private String maritalStatus;

	@OneToMany(mappedBy = "maritalStatus")
	private List<Client> clients;

	public MaritalStatus() {
		super();
	}

	public MaritalStatus(String maritalShort, String maritalStatus) {
		super();
		this.maritalShort = maritalShort;
		this.maritalStatus = maritalStatus;
	}

	public String getMaritalShort() {
		return maritalShort;
	}

	public void setMaritalShort(String maritalShort) {
		this.maritalShort = maritalShort;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

}