package cz.moravec;

import cz.moravec.model.Country;
import cz.moravec.service.CountryService;
import cz.moravec.web.RestApi;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {App.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles({"test"})
public class CountryServiceTests {


    private final String TEST_URL = "http://localhost:8080";
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(TEST_URL).addConverterFactory(JacksonConverterFactory.create()).build();
    private RestApi restService = retrofit.create(RestApi.class);

    @Autowired
    private CountryService countryService;

    @Test
    public void createCountryTest() throws IOException {
        Country country = createCountry();
        long count1 = countryService.getCount();
        Response<Country> response = restService.createCountry(country).execute();
        long count2 = countryService.getCount();
        List<Country> all = countryService.getAll(PageRequest.of(0, 10));
        Country last = all.get(all.size() - 1);
        assertEquals("Country has not been created", count1 + 1, count2);
        assertNotNull("Response is null", response.body());
        assertEquals("Created country does not equal requested", last.toString(), response.body().toString());
    }

    @Test
    public void deleteCountryTest() throws IOException {

        Country newCountry = createCountry();
        countryService.save(newCountry);
        restService.deleteCountry(newCountry.getId()).execute();
        Optional<Country> country = countryService.get(newCountry.getId());

        assertTrue("Country should be deleted", !country.isPresent());
    }

    @Test
    public void updateCountryTest() throws IOException {

        Country newCountry = createCountry();
        countryService.save(newCountry);
        List<Country> all = countryService.getAll(PageRequest.of(0, 10));
        Country someCountry = all.get(0);
        someCountry.setName("Britannia");
        Response<Country> response = restService.updateCountry(someCountry).execute();

        assertNotNull("Response is null!", response);
        Country result = response.body();
        assertNotNull("Resulted country is null", result);
        assertEquals("Country name should be updated", someCountry.getName(), result.getName());
    }

    @Test
    public void getCountry() throws IOException {

        Country country = createCountry();
        countryService.save(country);
        Iterable<Country> all = countryService.getAll(PageRequest.of(0, 10));
        Country someCountry = all.iterator().next();
        Response<Country> execute = restService.getCountry(someCountry.getId()).execute();
        Country countryFromRest = execute.body();
        assertNotNull("Retrieved country is null", countryFromRest);
        assertEquals("Retrieved country id should match created country id.", someCountry.getId(), countryFromRest.getId());
        assertEquals("Retrieved country name should match created country name.", someCountry.getName(), countryFromRest.getName());
    }

    @Test
    public void getCountries() throws IOException {

        List<Country> countries = createCountries();
        countryService.save(countries);
        Response<List<Country>> execute = restService.getCountries().execute();
        List<Country> result = execute.body();
        assertNotNull("Result is null!", result);
        List<Country> all = countryService.getAll(PageRequest.of(0, 100));
        assertEquals("Result should have same number of items!", result.size(), all.size());
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
