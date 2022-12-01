package vnjip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.base.BillingCurrency;

public interface BillingCurrencyRepository extends JpaRepository<BillingCurrency, String> {
}
