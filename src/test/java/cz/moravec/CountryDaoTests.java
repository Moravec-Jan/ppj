package cz.moravec;

import cz.moravec.data.Country;
import cz.moravec.data.CountryDao;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest // replacement for deprecated SpringApplicationConfiguration
@ActiveProfiles({"test"})
public class CountryDaoTests {

    @Autowired
    private CountryDao countryDao;


    @Test
    public void createCountryTest() {
        Country country = createCountry();
        countryDao.create(country);
        assertTrue("Country creation should return true", countryDao.getCountries().size() > 0);
    }

    @Test
    public void deleteCountryTest() {

        Country newCountry = createCountry();
        int id = countryDao.create(newCountry);
        int count1 = countryDao.getCountries().size();
        Country country = countryDao.getCountry(id);

        boolean result = countryDao.delete(country.getId());
        int count2 = countryDao.getCountries().size();
        assertTrue("Delete method should return true", result);
        assertEquals("One country should be deleted", count1, count2 + 1);
    }

    @Test
    public void deleteCountriesTest() {

        List<Country> countries = createCountries();
        countryDao.create(countries.get(0));
        countryDao.create(countries.get(1));
        countryDao.deleteCountries();
        assertEquals("All countries should be deleted", 0, countryDao.getCountries().size());
    }

    @Test
    public void createCountriesTest() {

        countryDao.deleteCountries();
        List<Country> countries = createCountries();
        countryDao.create(countries);

        List<Country> countriesFromDb = countryDao.getCountries();
        assertEquals("2 countries should be created", 2, countriesFromDb.size());
    }

    @Test
    public void getCountriesTest() {

        Country country = createCountry();
        countryDao.create(country);
        List<Country> countries = countryDao.getCountries();
        assertTrue("There should be countries in database!", countries.size() >= 1);

        Country countryFromDb = countries.get(countries.size() - 1);
        assertEquals("Retrieved country should match created country.", country.getName(), countryFromDb.getName());
    }


    @Test
    public void updateCountyTest() {
        Country country = createCountry();
        countryDao.create(country);
        List<Country> countries = countryDao.getCountries();
        Country countryFromDb = countries.get(0);
        String name = "Updated country name.";
        countryFromDb.setName(name);
        countryDao.update(countryFromDb);
        countryFromDb = countries.get(0);
        assertEquals("Country update should return true", countryFromDb.getName(), name);

        Country updated = countryDao.getCountry(countryFromDb.getId());
        assertEquals("Updated country should match retrieved updated offer", countryFromDb.getName(), updated.getName());
    }

    @Test
    public void getCountryByIdTest() {
        Country country = createCountry();
        countryDao.create(country);
        List<Country> countries = countryDao.getCountries();
        Country countryFromDb = countries.get(0);
        Country retrievedCountryById = countryDao.getCountry(countryFromDb.getId());
        assertEquals("Country by ID should match country from list.", countryFromDb.getName(), retrievedCountryById.getName());

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
