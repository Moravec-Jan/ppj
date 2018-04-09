package cz.moravec;

import cz.moravec.data.Country;
import cz.moravec.data.CountryDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Main.class}) // replacement for deprecated SpringApplicationConfiguration
@ActiveProfiles({"test"})
public class CountryDaoTests {

    @Autowired
    private CountryDao countryDao;


    @Test
    public void createCountryTest() {
        Country country = createCountry();
        assertTrue("Country creation should return true", countryDao.create(country));
    }

    @Test
    public void deleteCountryTest() {

        Country newCountry = createCountry();
        countryDao.create(newCountry);
        int count1 = countryDao.getCountries().size();
        Country country = countryDao.getCountry(1);

        countryDao.delete(country.getId());
        int count2 = countryDao.getCountries().size();
        assertTrue("One country should be deleted", count1 == count2 + 1);
    }

    @Test
    public void deleteCountriesTest() {

        List<Country> countries = createCountries();
        countryDao.create(countries.get(0));
        countryDao.create(countries.get(1));
        countryDao.deleteCountries();
        assertTrue("All countries should be deleted", countryDao.getCountries().size() == 0);
    }

    @Test
    public void createCountriesTest() {

        countryDao.deleteCountries();
        List<Country> countries = createCountries();
        int[] ids = countryDao.create(countries);
        assertTrue("Countries should be created", ids != null && ids.length == 2);

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
        countryFromDb.setName("Updated country name.");
        assertTrue("Country update should return true", countryDao.update(countryFromDb));

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
        Country country2 = new Country(1, "Brno");
        List<Country> countries = new ArrayList<>();
        countries.add(country);
        countries.add(country2);
        return countries;
    }

    private Country createCountry() {
        return new Country(0, "Prague");
    }
}
