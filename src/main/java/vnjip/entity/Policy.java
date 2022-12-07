package vnjip.entity;

import java.util.Date;

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
import vnjip.model.BaseModel;

@Entity
@Table(name = "policy")
public class Policy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "policy_number", nullable = false, unique = true)
	private Long policyNumber;

	/* Start VNIJIP */
	private Date inceptionDate;

	private Date expiryDate;

	@OneToOne
	@JoinColumn(name = "client_number", nullable = true)
	private Client policyOwner;

	private String engineNo;
	private String chassisNo;
	private String vehicleRegistrationNo;

	@ManyToOne
	@JoinColumn(name = "currency_short", nullable = true)
	private BillingCurrency billingCurrency;

	private String sumInsured;
	private double rate;
	private double annualPremium;
	private double postedPremium;

	@ManyToOne
	@JoinColumn(name = "policy_status_short", nullable = true)
	private PolicyStatus policyStatus;
	/* End VNIJIP */

	public Policy() {
		super();
	}

	public Policy(BaseModel baseModel, Client client) {
		super();
		this.inceptionDate = baseModel.getInceptionDate();
		this.expiryDate = baseModel.getExpiryDate();
		this.policyOwner = client;
		this.engineNo = baseModel.getEngineNo();
		this.chassisNo = baseModel.getChassisNo();
		this.vehicleRegistrationNo = baseModel.getVehicleRegistrationNo();
		this.billingCurrency = baseModel.getBillingCurrency();
		this.sumInsured = baseModel.getSumInsured();
		this.rate = baseModel.getRate();
		this.annualPremium = baseModel.getAnnualPremium();
		this.postedPremium = baseModel.getPostedPremium();

	}

	public Policy(Policy policy) {
		super();
		this.inceptionDate = policy.getInceptionDate();
		this.expiryDate = policy.getExpiryDate();
		this.policyOwner = policy.getPolicyOwner();
		this.engineNo = policy.getEngineNo();
		this.chassisNo = policy.getChassisNo();
		this.vehicleRegistrationNo = policy.getVehicleRegistrationNo();
		this.billingCurrency = policy.getBillingCurrency();
		this.sumInsured = policy.getSumInsured();
		this.rate = policy.getRate();
		this.annualPremium = policy.getAnnualPremium();
		this.postedPremium = policy.getPostedPremium();
		this.policyStatus = policy.getPolicyStatus();
	}

	public Long getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(Long policyNumber) {
		this.policyNumber = policyNumber;
	}

	public Date getInceptionDate() {
		return inceptionDate;
	}

	public void setInceptionDate(Date inceptionDate) {
		this.inceptionDate = inceptionDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Client getPolicyOwner() {
		return policyOwner;
	}

	public void setPolicyOwner(Client policyOwner) {
		this.policyOwner = policyOwner;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getChassisNo() {
		return chassisNo;
	}

	public void setChassisNo(String chassisNo) {
		this.chassisNo = chassisNo;
	}

	public String getVehicleRegistrationNo() {
		return vehicleRegistrationNo;
	}

	public void setVehicleRegistrationNo(String vehicleRegistrationNo) {
		this.vehicleRegistrationNo = vehicleRegistrationNo;
	}

	public BillingCurrency getBillingCurrency() {
		return billingCurrency;
	}

	public void setBillingCurrency(BillingCurrency billingCurrency) {
		this.billingCurrency = billingCurrency;
	}

	public String getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(String sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getAnnualPremium() {
		return annualPremium;
	}

	public void setAnnualPremium(double annualPremium) {
		this.annualPremium = annualPremium;
	}

	public double getPostedPremium() {
		return postedPremium;
	}

	public void setPostedPremium(double postedPremium) {
		this.postedPremium = postedPremium;
	}

	public PolicyStatus getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(PolicyStatus policyStatus) {
		this.policyStatus = policyStatus;
	}

}