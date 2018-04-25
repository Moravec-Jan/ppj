package cz.moravec.repository;

import cz.moravec.model.Country;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {
}
