package vnjip.entity.base;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import vnjip.entity.Client;

@Entity
@Table(name = "country")
public class Country {
	@Id
	@Column(name = "country_short", nullable = true, length = 3)
	private String countryShort;
	@Column(name = "country_name", nullable = true, length = 15)
	private String countryName;

	@OneToMany(mappedBy = "country")
	private List<Client> clients;

	public Country() {
		super();
	}

	public Country(String countryShort, String countryName) {
		super();
		this.countryShort = countryShort;
		this.countryName = countryName;
	}

	public String getCountryShort() {
		return countryShort;
	}

	public void setCountryShort(String countryShort) {
		this.countryShort = countryShort;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

}