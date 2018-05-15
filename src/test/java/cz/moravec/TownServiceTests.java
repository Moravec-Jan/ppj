package cz.moravec;

import cz.moravec.config.Conditions;
import cz.moravec.model.Country;
import cz.moravec.model.Town;
import cz.moravec.service.CountryService;
import cz.moravec.service.TownService;
import cz.moravec.web.rest.RestApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {App.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"weather.read-only-mode = false"})
@ActiveProfiles({"test"})
public class TownServiceTests {
    private static int counter = 1;

    @Autowired
    private CountryService countryService;
    @Autowired
    private TownService townService;

    private final String TEST_URL = "http://localhost:8080";
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(TEST_URL).addConverterFactory(JacksonConverterFactory.create()).build();
    private RestApi restService = retrofit.create(RestApi.class);

    @Conditional(Conditions.ReadOnlyModeDisabled.class)
    @Test
    public void createTownTest() throws IOException {
        Town town = createTown();
        Response<Town> response = restService.createTown(town).execute();
        Optional<Town> created = townService.get(town.getId());
        assertTrue("Town has not been created", created.isPresent());
        assertNotNull("Response is null", response.body());
        assertEquals("Created Town does not equal requested", created.get().toString(), response.body().toString());
    }

    @Conditional(Conditions.ReadOnlyModeDisabled.class)
    @Test
    public void deleteTownTest() throws IOException {

        Town newTown = createTown();
        townService.save(newTown);
        restService.deleteTown(newTown.getId()).execute();
        Optional<Town> town = townService.get(newTown.getId());

        assertTrue("Town should be deleted", !town.isPresent());
    }

    @Conditional(Conditions.ReadOnlyModeDisabled.class)
    @Test
    public void updateTownTest() throws IOException {

        Town newTown = createTown();
        townService.save(newTown);
        newTown.setName("York");
        Response<Town> response = restService.updateTown( newTown).execute();

        assertNotNull("Response is null!", response);
        Town result = response.body();
        assertNotNull("Resulted town is null", result);
        assertEquals("Town should be updated",  newTown.toString(), result.toString());
    }

    @Test
    public void getTown() throws IOException {

        Town town = createTown();
        townService.save(town);
        Response<Town> execute = restService.getTown(town.getId()).execute();
        Town townFromRest = execute.body();
        assertNotNull("Retrieved town is null", townFromRest);
        assertEquals("Retrieved town should match created Town.", town.toString(), townFromRest.toString());
    }

    @Test
    public void getTowns() throws IOException {

        List<Town> towns = createTowns();
        townService.save(towns);
        Response<List<Town>> execute = restService.getTowns().execute();
        List<Town> result = execute.body();
        assertNotNull("Result is null!", result);
        List<Town> all = townService.getAll(PageRequest.of(0, 100));
        assertEquals("Result should have same number of items!", result.size(), all.size());
    }


    //support methods for tests

    private List<Town> createTowns() {
        Country country = new Country("Czech Republic");
        countryService.save(country);
        Town town = new Town(counter++, "Prague", country);
        Town town2 = new Town(counter++, "Brno", country);
        List<Town> countries = new ArrayList<>();
        countries.add(town);
        countries.add(town2);
        return countries;
    }

    private Town createTown() {
        Country country = new Country("Czech Republic");
        countryService.save(country);
        return new Town(counter++, "Prague", country);
    }
}
