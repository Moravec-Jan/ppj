package cz.moravec;

import cz.moravec.model.Country;
import cz.moravec.repository.CountryRepository;
import cz.moravec.service.CountryService;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class CountryServiceTests {

    @Autowired
    private CountryService countryService;

    @Test
    public void createCountryTest() {
        Country country = createCountry();
        countryService.save(country);
        assertTrue("Country creation has failed", countryService.exists(country.getId()));
    }

    @Test
    public void deleteCountryTest() {

        Country newCountry = createCountry();
        Country country = countryService.save(newCountry);
        long count1 = countryService.getCount();

        boolean result = countryService.delete(country);
        long count2 = countryService.getCount();
        assertTrue("Delete method should return true", result);
        assertEquals("One country should be deleted", count1, count2 + 1);
    }

    @Test
    public void deleteCountriesTest() {

        List<Country> countries = createCountries();
        countryService.save(countries.get(0));
        countryService.save(countries.get(1));
        boolean result = countryService.deleteAll();
        assertTrue("All countries should be deleted", result);
    }

    @Test
    public void createCountriesTest() {

        countryService.deleteAll();
        List<Country> countries = createCountries();
        countryService.save(countries);

        assertEquals("2 countries should be created", 2, countryService.getCount());
    }

    @Test
    public void getCountriesTest() {

        Country country = createCountry();
        countryService.save(country);
        long count = countryService.getCount();
        assertTrue("There should be countries in database!", count >= 1);
        Iterable<Country> all = countryService.getAll();
        Optional<Country> countryFromDb = countryService.get(all.iterator().next().getId());
        assertTrue("Retrieved country is null", countryFromDb.isPresent());
        assertEquals("Retrieved country should match created country.", country.getName(), countryFromDb.get().getName());

    }


    @Test
    public void updateCountyTest() {
        Country country = createCountry();
        countryService.save(country);
        Iterable<Country> countries = countryService.getAll();
        Country countryFromDb = countries.iterator().next();
        String name = "Updated country name.";
        countryFromDb.setName(name);
        countryService.save(countryFromDb);

        Optional<Country> countryDb = countryService.get(countryFromDb.getId());
        assertTrue("Retrieved country is null", countryDb.isPresent());
        assertEquals("Update did not work, countries are not same", countryDb.get().getName(), name);
    }

    @Test
    public void getCountryByIdTest() {
        Country country = createCountry();
        countryService.save(country);
        Iterable<Country> countries = countryService.getAll();
        Country countryFromDb = countries.iterator().next();
        Optional<Country> retrievedCountryById = countryService.get(countryFromDb.getId());
        assertTrue("Retrieved country is null", retrievedCountryById.isPresent());
        assertEquals("Country by ID should match country from list.", countryFromDb.getName(), retrievedCountryById.get().getName());

    }

    //support methods for tests

    private List<Country> createCountries() {
        Country country = createCountry();
        Country country2 = new Country("Czech Republic");
        List<Country> countries = new ArrayList<Country>();
        countries.add(country);
        countries.add(country2);
        return countries;
    }

    private Country createCountry() {
        return new Country("USA");
    }
}
