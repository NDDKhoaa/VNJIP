package vnjip.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vnjip.entity.base.BillingCurrency;
import vnjip.repository.BillingCurrencyRepository;
import vnjip.services.BillingCurrencyService;

@Service
public class BillingCurrencyServiceImpl implements BillingCurrencyService {

	@Autowired
	private BillingCurrencyRepository billingCurrencyRepository;

	public List<BillingCurrency> listAll() {
		return (List<BillingCurrency>) billingCurrencyRepository.findAll();
	}

	public void save(BillingCurrency billingCurrency) {
		billingCurrencyRepository.save(billingCurrency);
	}

	public BillingCurrency findByShort(String currencyShort) {
		Optional<BillingCurrency> rs = billingCurrencyRepository.findById(currencyShort);
		return rs.orElse(null);
	}

	public void delete(BillingCurrency billingCurrency) {
		billingCurrencyRepository.delete(billingCurrency);
	}

	public void deleteByShort(String currencyShort) {
		billingCurrencyRepository.deleteById(currencyShort);
	}

}
