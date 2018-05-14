package cz.moravec.web.rest;

import cz.moravec.model.Country;
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
    public ResponseEntity<List<Country>> getCountries() {
        List<Country> countries = countryService.getAll(PageRequest.of(0, 100)); // only first 100
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @RequestMapping(value = RestApi.COUNTRIES_PATH, method = RequestMethod.POST)
    public ResponseEntity<Country> createCountry(@RequestBody Country country) {
        if (countryService.exists(country.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            countryService.save(country);
            return new ResponseEntity<>(country, HttpStatus.OK);

        }
    }

    @RequestMapping(value = RestApi.COUNTRY_PATH, method = RequestMethod.POST)
    public ResponseEntity<Country> updateCountry(@RequestBody Country country) {
        Optional<Country> requestedCountry = countryService.get(country.getId());
        if (requestedCountry.isPresent()) {
            Country updatedCountry = countryService.save(country);
            return new ResponseEntity<>(updatedCountry, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = RestApi.COUNTRY_PATH, method = RequestMethod.GET)
    public ResponseEntity<Country> getCountry(@PathVariable("id") long id) {
        Optional<Country> country = countryService.get(id);
        if (!country.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(country.get(), HttpStatus.OK);
    }

    @RequestMapping(value = RestApi.COUNTRY_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteCountry(@PathVariable("id") long id) {
        Optional<Country> country = countryService.get(id);
        if (!country.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            countryService.delete(country.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
