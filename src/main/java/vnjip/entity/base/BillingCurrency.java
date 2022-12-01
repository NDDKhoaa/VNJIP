package vnjip.entity.base;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import vnjip.entity.Policy;

@Entity
@Table(name = "billingcurrency")
public class BillingCurrency {
	@Id
	@Column(name = "currency_short", nullable = true, length = 3)
	private String currencyShort;
	@Column(name = "currency_name", nullable = true, length = 30)
	private String currencyName;

	@OneToMany(mappedBy = "billingCurrency")
	private List<Policy> policies;

	public BillingCurrency() {
		super();
	}

	public String getCurrencyShort() {
		return currencyShort;
	}

	public void setCurrencyShort(String currencyShort) {
		this.currencyShort = currencyShort;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public List<Policy> getPolicies() {
		return policies;
	}

	public void setPolicies(List<Policy> policies) {
		this.policies = policies;
	}

}