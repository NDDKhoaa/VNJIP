package vnjip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vnjip.entity.base.Gender;

public interface GenderRepository extends JpaRepository<Gender, String> {
}
