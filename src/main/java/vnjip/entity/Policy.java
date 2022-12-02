package vnjip.entity;

import java.sql.Timestamp; //VNIJIP-intern

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne; //VNIJIP-intern
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

/* Start VNIJIP */
	private Timestamp inceptionDate;
	
	private Timestamp expiryDate;
	
	@OneToOne
	@JoinColumn(name = "client_number", nullable = true)
	private Client policyOwner;
	
	private String engineNo;
	private String chassisNo;
	private String vehicleRegistrationNo;
	
	@ManyToOne
	@JoinColumn(name = "currency_short", nullable = true)
	private BillingCurrency billingCurrency;
	
	private double sumInsured;
	private double rate;
	private double annualPremium;
	private double postedPremium;

	@ManyToOne
	@JoinColumn(name = "policy_status_short", nullable = true)
	private PolicyStatus policyStatus;
/* End VNIJIP */
}