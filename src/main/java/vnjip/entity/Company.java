package vnjip.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {
	@Id
	@Column(name = "company_code", nullable = false, unique = true)
	private long companyCode;
	@Column(name = "company_name", nullable = true, length = 200)
	private String companyName;

	@OneToMany(mappedBy = "company")
	private List<Agent> agents;

	public Company() {
		super();
	}

	public Company(long companyCode, String companyName) {
		super();
		this.companyCode = companyCode;
		this.companyName = companyName;
	}

	public long getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(long companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

}