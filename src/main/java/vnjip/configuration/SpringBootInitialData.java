package vnjip.configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import vnjip.entity.Account;
import vnjip.entity.base.AccountStatus;
import vnjip.entity.base.AccountType;
import vnjip.entity.base.BillingCurrency;
import vnjip.entity.base.Country;
import vnjip.entity.base.ErrorPf;
import vnjip.entity.base.Gender;
import vnjip.entity.base.MaritalStatus;
import vnjip.entity.base.PolicyStatus;
import vnjip.entity.base.Role;
import vnjip.entity.enumtype.PrivilegesEnum;
import vnjip.services.Impl.AccountServiceImpl;
import vnjip.services.Impl.AccountStatusServiceImpl;
import vnjip.services.Impl.AccountTypeServiceImpl;
import vnjip.services.Impl.BillingCurrencyServiceImpl;
import vnjip.services.Impl.CountryServiceImpl;
import vnjip.services.Impl.ErrorPfImpl;
import vnjip.services.Impl.GenderServiceImpl;
import vnjip.services.Impl.MaritalStatusServiceImpl;
import vnjip.services.Impl.PolicyStatusServiceImpl;
import vnjip.services.Impl.RoleServiceImpl;

@Component
public class SpringBootInitialData implements ApplicationRunner {

	@Autowired
	private AccountServiceImpl userRepository;
	@Autowired
	private RoleServiceImpl roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private AccountStatusServiceImpl accountStatusServiceImpl;
	@Autowired
	private AccountTypeServiceImpl accountTypeServiceImpl;
	@Autowired
	private BillingCurrencyServiceImpl billingCurrencyServiceImpl;
	@Autowired
	private CountryServiceImpl countryServiceImpl;
	@Autowired
	private GenderServiceImpl genderServiceImpl;
	@Autowired
	private MaritalStatusServiceImpl maritalStatusServiceImpl;
	@Autowired
	private PolicyStatusServiceImpl policyStatusServiceImpl;
	@Autowired
	private ErrorPfImpl errorPfImpl;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<Role> roleAdmin = roleRepository.listAll();
		if (roleAdmin.isEmpty()) {

			/* Role Entity Start */
			Set<PrivilegesEnum> privilegesEnumSet = new HashSet<>();
			privilegesEnumSet.add(PrivilegesEnum.ROLE_ADMIN);
			Set<PrivilegesEnum> privilegesEnumSet2 = new HashSet<>();
			privilegesEnumSet2.add(PrivilegesEnum.ROLE_AGENT);
			Set<PrivilegesEnum> privilegesEnumSet3 = new HashSet<>();
			privilegesEnumSet3.add(PrivilegesEnum.ROLE_CLIENT);
			Role roleAdmin1 = new Role("ROLE_ADMIN", privilegesEnumSet);
			Role roleAgent = new Role("ROLE_AGENT", privilegesEnumSet);
			Role roleClient = new Role("ROLE_CLIENT", privilegesEnumSet);
			roleRepository.save(roleAdmin1);
			roleRepository.save(roleAgent);
			roleRepository.save(roleClient);
			/* Role Entity End */

			/* Account Status Start */
			AccountStatus accountStatusActive = new AccountStatus("A", "Active");
			AccountStatus accountStatusTerminated = new AccountStatus("T", "Terminated");
			accountStatusServiceImpl.save(accountStatusActive);
			accountStatusServiceImpl.save(accountStatusTerminated);
			/* Account Status End */

			/* Account Tyoe Start */
			AccountType accountTypeAgent = new AccountType("A", "Agent");
			AccountType accountTypeBroker = new AccountType("B", "Broker");
			AccountType accountTypeCoinsurer = new AccountType("C", "Coinsurer");
			AccountType accountTypeReinsurer = new AccountType("R", "Reinsurer");
			accountTypeServiceImpl.save(accountTypeAgent);
			accountTypeServiceImpl.save(accountTypeBroker);
			accountTypeServiceImpl.save(accountTypeCoinsurer);
			accountTypeServiceImpl.save(accountTypeReinsurer);
			/* Account Type End */

			/* Billing Currency Start */
			BillingCurrency billingCurrencyVND = new BillingCurrency("VND", "Vietnam Dong");
			BillingCurrency billingCurrencyUSD = new BillingCurrency("USD", "US Dollar");
			BillingCurrency billingCurrencySGD = new BillingCurrency("SGD", "Singapore Dollar");
			billingCurrencyServiceImpl.save(billingCurrencyVND);
			billingCurrencyServiceImpl.save(billingCurrencyUSD);
			billingCurrencyServiceImpl.save(billingCurrencySGD);
			/* Billing Currency End */

			/* Country Start */
			Country countryVNI = new Country("VNI", "Vietnam");
			Country countryUSA = new Country("USA", "United State");
			Country countrySIN = new Country("SIN", "Singapore");
			Country countryMAL = new Country("MAL", "Malaysia");
			countryServiceImpl.save(countryVNI);
			countryServiceImpl.save(countryUSA);
			countryServiceImpl.save(countrySIN);
			countryServiceImpl.save(countryMAL);
			/* Country End */

			/* Gender Start */
			Gender genderM = new Gender("M", "Male");
			Gender genderF = new Gender("F", "Female");
			Gender genderU = new Gender("U", "Unknown");
			genderServiceImpl.save(genderM);
			genderServiceImpl.save(genderF);
			genderServiceImpl.save(genderU);
			/* Gender End */

			/* Marital Status Start */
			MaritalStatus maritalStatusM = new MaritalStatus("M", "Married");
			MaritalStatus maritalStatusS = new MaritalStatus("S", "Single");
			MaritalStatus maritalStatusD = new MaritalStatus("D", "Divorced");
			maritalStatusServiceImpl.save(maritalStatusM);
			maritalStatusServiceImpl.save(maritalStatusS);
			maritalStatusServiceImpl.save(maritalStatusD);
			/* Marital Status End */

			/* Policy Status Start */
			PolicyStatus policyStatusPN = new PolicyStatus("PN", "Pending");
			PolicyStatus policyStatusIF = new PolicyStatus("IF", "Pending");
			policyStatusServiceImpl.save(policyStatusPN);
			policyStatusServiceImpl.save(policyStatusIF);
			/* Policy Status End */

			/* Error Start */
			ErrorPf error181 = new ErrorPf("E181", "Expiry Date Must > Inception Date");
			ErrorPf error182 = new ErrorPf("E182", "Must Be An Existing Client Number");
			ErrorPf error183 = new ErrorPf("E183", "Combination Of Engine No And Chassis No Must Be Unique");
			ErrorPf error184 = new ErrorPf("E184", "Must Not Be Negative");
			ErrorPf error186 = new ErrorPf("E186", "Must Be Enter");
			ErrorPf error189 = new ErrorPf("E189", "Is Error Type");
			errorPfImpl.save(error181);
			errorPfImpl.save(error182);
			errorPfImpl.save(error183);
			errorPfImpl.save(error184);
			errorPfImpl.save(error186);
			errorPfImpl.save(error189);
			/* Error End */

			/* Admin Account Start */
			roleAdmin = roleRepository.listAll();
			String pwdEncrypt = bCryptPasswordEncoder.encode("123456");
			AccountStatus accountStatus = accountStatusServiceImpl.findByShort("A");
			Account account = new Account("admin", "admin@gmail.com", pwdEncrypt, new HashSet<>(roleAdmin),
					accountStatus);
			userRepository.save(account);
			/* Admin Account End */
		}
	}
}
