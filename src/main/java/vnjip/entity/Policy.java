package vnjip.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import vnjip.entity.base.BillingCurrency;
import vnjip.entity.base.PolicyStatus;

@Entity
@Table(name = "policy")
public class Policy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "policy_number", nullable = false, unique = true)
	private Long policyNumber;

	@ManyToOne
	@JoinColumn(name = "policy_status_short", nullable = true)
	private PolicyStatus policyStatus;

	@ManyToOne
	@JoinColumn(name = "currency_short", nullable = true)
	private BillingCurrency billingCurrency;
}