package cz.moravec.web.rest;

import cz.moravec.model.Town;
import cz.moravec.service.TownService;
import cz.moravec.web.rest.RestApi;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TownRestController {

    private final TownService townService;

    public TownRestController(TownService townService) {
        this.townService = townService;
    }

    @RequestMapping(value = RestApi.TOWNS_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Town>> showTowns() {
        List<Town> towns = townService.getAll(PageRequest.of(0, 100));
        return new ResponseEntity<>(towns, HttpStatus.OK);
    }

    @RequestMapping(value = RestApi.TOWNS_PATH, method = RequestMethod.POST)
    public ResponseEntity<Town> addTown(@RequestBody Town town) {
        if (townService.exists(town.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            townService.save(town);
            return new ResponseEntity<>(town, HttpStatus.OK);

        }
    }

    @RequestMapping(value = RestApi.TOWN_PATH, method = RequestMethod.POST)
    public ResponseEntity<Town> updateTown(@RequestBody Town Town) {
        Optional<Town> requestedTown = townService.get(Town.getId());
        if (requestedTown.isPresent()) {
            Town updatedTown = townService.save(Town);
            return new ResponseEntity<>(updatedTown, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = RestApi.TOWN_PATH, method = RequestMethod.GET)
    public ResponseEntity<Town> getTown(@PathVariable("id") long id) {
        Optional<Town> town = townService.get(id);
        if (!town.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(town.get(), HttpStatus.OK);

    }

    @RequestMapping(value = RestApi.TOWN_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteTown(@PathVariable("id") long id) {
        Optional<Town> town = townService.get(id);
        if (!town.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            townService.delete(town.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
