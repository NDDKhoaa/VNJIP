package vnjip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.base.Country;

public interface CountryRepository extends JpaRepository<Country, String> {
}
