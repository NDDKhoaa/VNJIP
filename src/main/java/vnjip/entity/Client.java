package vnjip.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import vnjip.entity.base.Country;
import vnjip.entity.base.Gender;
import vnjip.entity.base.MaritalStatus;
import vnjip.model.BaseModel;

@Entity
@Table(name = "client")
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "client_number", nullable = false, unique = true)
	private Long clientNumber;
	@Column(name = "first_name", nullable = true, length = 60)
	private String firstName;
	@Column(name = "last_name", nullable = true, length = 60)
	private String lastName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_of_birth", nullable = true)
	private Date dateOfBirth;
	@Column(name = "identity_number", nullable = true, length = 15)
	private String identityNumber;
	@Column(name = "address", nullable = true, length = 120)
	private String address;

	@ManyToOne
	@JoinColumn(name = "gender_short", nullable = true)
	private Gender gender;

	@ManyToOne
	@JoinColumn(name = "marital_short", nullable = true)
	private MaritalStatus maritalStatus;

	@ManyToOne
	@JoinColumn(name = "country_short", nullable = true)
	private Country country;

	@OneToOne
	@JoinColumn(name = "account_number", nullable = true, unique = true)
	private Account account;

	public Client() {
		super();
	}

	public Client(BaseModel model, Gender gender2, Country country2, MaritalStatus maritalStatus2) {
		this.firstName = model.getClientFirstName();
		this.lastName = model.getClientLastName();
		this.dateOfBirth = model.getClientDOB();
		this.identityNumber = model.getClientIdentityNumber();
		this.address = model.getClientAddress();
		this.gender = gender2;
		this.maritalStatus = maritalStatus2;
		this.country = country2;
	}

	public Long getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(Long clientNumber) {
		this.clientNumber = clientNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}