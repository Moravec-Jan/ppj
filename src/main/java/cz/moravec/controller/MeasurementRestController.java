package cz.moravec.controller;

import cz.moravec.core.RestApi;
import cz.moravec.model.Measurement;
import cz.moravec.service.MeasurementService;
import cz.moravec.service.MeasurementService;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MeasurementRestController {

    private final MeasurementService measurementService;

    public MeasurementRestController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @RequestMapping(value = RestApi.MEASUREMENTS_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Measurement>> showMeasurements() {
        List<Measurement> measurements = measurementService.getAll(PageRequest.of(0, 100));
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }

    @RequestMapping(value = RestApi.MEASUREMENTS_PATH, method = RequestMethod.POST)
    public ResponseEntity<Measurement> addMeasurement(@RequestBody Measurement town) {
        if (measurementService.exists(town.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            measurementService.save(town);
            return new ResponseEntity<>(town, HttpStatus.OK);

        }
    }

    @RequestMapping(value = RestApi.MEASUREMENT_PATH, method = RequestMethod.GET)
    public ResponseEntity<Measurement> getMeasurement(@PathVariable("id") String id) {
        Optional<Measurement> town = measurementService.get(new ObjectId(id));
        if (!town.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(town.get(), HttpStatus.OK);

    }

    @RequestMapping(value = RestApi.MEASUREMENT_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteMeasurement(@PathVariable("id") String id) {
        Optional<Measurement> town = measurementService.get(new ObjectId(id));
        if (!town.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            measurementService.delete(town.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
