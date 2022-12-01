package vnjip.services;

import java.util.List;

import vnjip.entity.base.BillingCurrency;

public interface BillingCurrencyService {

	public List<BillingCurrency> listAll();

	public void save(BillingCurrency billingCurrency);

	public BillingCurrency findByShort(String currencyShort);

	public void delete(BillingCurrency billingCurrency);

	public void deleteByShort(String currencyShort);

}
