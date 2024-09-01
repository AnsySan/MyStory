package com.clone.twitter.userservice.repository;

import com.clone.twitter.userservice.model.country.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {
}