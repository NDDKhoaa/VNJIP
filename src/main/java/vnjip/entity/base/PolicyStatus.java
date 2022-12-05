package vnjip.entity.base;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import vnjip.entity.Policy;

@Entity
@Table(name = "policyStatus")
public class PolicyStatus {
	@Id
	@Column(name = "policy_status_short", nullable = true, length = 2)
	private String policyStatusShort;
	@Column(name = "policy_status_desc", nullable = true, length = 15)
	private String policyStatusDesc;

	@OneToMany(mappedBy = "policyStatus")
	private List<Policy> policies;

	public PolicyStatus() {
		super();
	}

	public PolicyStatus(String policyStatusShort, String policyStatusDesc) {
		super();
		this.policyStatusShort = policyStatusShort;
		this.policyStatusDesc = policyStatusDesc;
	}

	public String getPolicyStatusShort() {
		return policyStatusShort;
	}

	public void setPolicyStatusShort(String policyStatusShort) {
		this.policyStatusShort = policyStatusShort;
	}

	public String getPolicyStatusDesc() {
		return policyStatusDesc;
	}

	public void setPolicyStatusDesc(String policyStatusDesc) {
		this.policyStatusDesc = policyStatusDesc;
	}

	public List<Policy> getPolicies() {
		return policies;
	}

	public void setPolicies(List<Policy> policies) {
		this.policies = policies;
	}

}