package cz.moravec;

import cz.moravec.data.Country;
import cz.moravec.data.CountryDao;
import cz.moravec.data.Town;
import cz.moravec.data.TownDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest // replacement for deprecated SpringApplicationConfiguration
@ActiveProfiles({"test"})
public class TownDaoTests {
    private static int counter = 1;

    @Autowired
    private CountryDao countryDao;


    @Autowired
    private TownDao townDao;


    @Test
    public void createTownTest() {
        Town town = createTown();
        townDao.create(town);
        assertTrue("Town creation should return true", townDao.getAll().size() > 0);
    }

    @Test
    public void deleteTownTest() {

        Town town = createTown();
        int id = townDao.create(town);
        int count1 = townDao.getAll().size();
        Town townDb = townDao.get(id);

        boolean result = townDao.deleteAll(townDb.getId());
        int count2 = townDao.getAll().size();
        assertTrue("Delete method should return true", result);
        assertEquals("One town should be deleted", count1, count2 + 1);
    }

    @Test
    public void deleteTownsTest() {

        List<Town> towns = createTowns();
        townDao.create(towns.get(0));
        townDao.create(towns.get(1));
        townDao.deleteAll();
        assertEquals("All towns should be deleted", 0, townDao.getAll().size());
    }

    @Test
    public void createTownsTest() {

        townDao.deleteAll();
        List<Town> towns = createTowns();
        townDao.create(towns);

        List<Town> townsFromDb = townDao.getAll();
        assertEquals("2 towns should be created", 2, townsFromDb.size());
    }

    @Test
    public void getTownsTest() {

        Town town = createTown();
        townDao.create(town);
        List<Town> towns = townDao.getAll();
        assertTrue("There should be towns in database!", towns.size() >= 1);

        Town townFromDb = towns.get(towns.size() - 1);
        assertEquals("Retrieved town should match created town.", town.getName(), townFromDb.getName());
    }


    @Test
    public void updateTownTest() {
        Town town = createTown();
        townDao.create(town);
        List<Town> countries = townDao.getAll();
        Town townFromDb = countries.get(0);
        String name = "Updated townname.";
        townFromDb.setName(name);
        townDao.update(townFromDb);
        townFromDb = countries.get(0);
        assertEquals("Town update should return true", townFromDb.getName(), name);

        Town updated = townDao.get(townFromDb.getId());
        assertEquals("Updated town should match retrieved updated offer", townFromDb.getName(), updated.getName());
    }

    @Test
    public void getTownByIdTest() {
        Town town = createTown();
        townDao.create(town);
        List<Town> towns = townDao.getAll();
        Town townFromDb = towns.get(0);
        Town retrievedTownById = townDao.get(townFromDb.getId());
        assertEquals("Town by ID should match town from list.", townFromDb.getName(), retrievedTownById.getName());

    }

    //support methods for tests

    private List<Town> createTowns() {
        Country country = new Country("Czech Republic");
        countryDao.create(country);
        Town town = new Town(counter++, "Prague", country);
        Town town2 = new Town(counter++, "Brno", country);
        List<Town> countries = new ArrayList<Town>();
        countries.add(town);
        countries.add(town2);
        return countries;
    }

    private Town createTown() {
        Country country = new Country("Czech Republic");
        countryDao.create(country);
        return new Town(counter++, "Prague", country);
    }
}
