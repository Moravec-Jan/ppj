package cz.moravec;

import cz.moravec.model.Country;
import cz.moravec.model.Town;
import cz.moravec.repository.CountryRepository;
import cz.moravec.repository.MeasurementRepository;
import cz.moravec.repository.TownRepository;
import cz.moravec.service.CountryService;
import cz.moravec.service.MeasurementService;
import cz.moravec.service.TownService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class TownServiceTests {
    private static int counter = 1;

    @Autowired
    private CountryService countryService;
    @Autowired
    private TownService townService;

    @Test
    public void createTownTest() {
        Town town = createTown();
        townService.save(town);
        assertTrue("Town creation should return true", townService.getCount() > 0);
    }


    @Test
    public void deleteTownTest() {

        Town newTown = createTown();
        Town Town = townService.save(newTown);
        long count1 = townService.getCount();

        boolean result = townService.delete(Town);
        long count2 = townService.getCount();
        assertTrue("Delete method should return true", result);
        assertEquals("One Town should be deleted", count1, count2 + 1);
    }

    @Test
    public void deleteCountriesTest() {

        List<Town> countries = createTowns();
        townService.save(countries.get(0));
        townService.save(countries.get(1));
        boolean result = townService.deleteAll();
        assertTrue("All countries should be deleted", result);
    }

    @Test
    public void createCountriesTest() {

        townService.deleteAll();
        List<Town> countries = createTowns();
        townService.save(countries);

        assertEquals("2 countries should be created", 2, townService.getCount());
    }

    @Test
    public void getCountriesTest() {

        Town Town = createTown();
        townService.save(Town);
        long count = townService.getCount();
        assertTrue("There should be countries in database!", count >= 1);

        Optional<Town> TownFromDb = townService.get((int) count - 1);
        assertTrue("Retrieved Town is null", TownFromDb.isPresent());
        assertEquals("Retrieved Town should match created Town.", Town.getName(), TownFromDb.get().getName());

    }


    @Test
    public void updateCountyTest() {
        Town Town = createTown();
        townService.save(Town);
        Iterable<Town> countries = townService.getAll(PageRequest.of(0,10));
        Town TownFromDb = countries.iterator().next();
        String name = "Updated Town name.";
        TownFromDb.setName(name);
        townService.save(TownFromDb);

        Optional<Town> TownDb = townService.get(TownFromDb.getId());
        assertTrue("Retrieved Town is null", TownDb.isPresent());
        assertEquals("Update did not work, countries are not same", TownDb.get().getName(), name);
    }

    @Test
    public void getTownByIdTest() {
        Town Town = createTown();
        townService.save(Town);
        Iterable<Town> countries = townService.getAll(PageRequest.of(0,10));
        Town TownFromDb = countries.iterator().next();
        Optional<Town> retrievedTownById = townService.get(TownFromDb.getId());
        assertTrue("Retrieved Town is null", retrievedTownById.isPresent());
        assertEquals("Town by ID should match Town from list.", TownFromDb.getName(), retrievedTownById.get().getName());

    }

    //support methods for tests

    private List<Town> createTowns() {
        Country country = new Country("Czech Republic");
        countryService.save(country);
        Town town = new Town(counter++, "Prague", country);
        Town town2 = new Town(counter++, "Brno", country);
        List<Town> countries = new ArrayList<Town>();
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
