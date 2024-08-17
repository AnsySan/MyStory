package com.clone.twitter.userservice.repository;

import com.clone.twitter.userservice.entity.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {
}