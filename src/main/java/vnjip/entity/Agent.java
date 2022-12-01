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

@Entity
@Table(name = "agent")
public class Agent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "agent_number", nullable = false, unique = true)
	private Long agentNumber;
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
	@JoinColumn(name = "gender_number", nullable = true)
	private Gender gender;

	@ManyToOne
	@JoinColumn(name = "marital_number", nullable = true)
	private MaritalStatus maritalStatus;

	@ManyToOne
	@JoinColumn(name = "country_number", nullable = true)
	private Country country;

	@OneToOne
	@JoinColumn(name = "account_number", nullable = true, unique = true)
	private Account account;
}