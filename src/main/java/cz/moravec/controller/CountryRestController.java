package cz.moravec.controller;

import cz.moravec.core.RestApi;
import cz.moravec.model.Country;
import cz.moravec.service.CountryService;
import cz.moravec.service.CountryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CountryRestController {

    private final CountryService countryService;

    public CountryRestController(CountryService countryService) {
        this.countryService = countryService;
    }

    @RequestMapping(value = RestApi.COUNTRIES_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Country>> showCountrys() {
        List<Country> countries = countryService.getAll(PageRequest.of(0,100));
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @RequestMapping(value = RestApi.COUNTRIES_PATH, method = RequestMethod.POST)
    public ResponseEntity<Country> addCountry(@RequestBody Country town) {
        if (countryService.exists(town.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            countryService.save(town);
            return new ResponseEntity<>(town, HttpStatus.OK);

        }
    }

    @RequestMapping(value = RestApi.COUNTRY_PATH, method = RequestMethod.GET)
    public ResponseEntity<Country> getCountry(@PathVariable("id") int id) {
        Optional<Country> town = countryService.get(id);
        if (!town.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(town.get(), HttpStatus.OK);

    }

    @RequestMapping(value = RestApi.COUNTRY_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteCountry(@PathVariable("id") int id) {
        Optional<Country> town = countryService.get(id);
        if (!town.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            countryService.delete(town.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
