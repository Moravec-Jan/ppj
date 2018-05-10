package cz.moravec.web.rest;

import cz.moravec.model.projections.MeasurementAverage;
import cz.moravec.model.projections.MeasurementData;
import cz.moravec.model.Measurement;
import cz.moravec.service.MeasurementService;
import cz.moravec.web.rest.RestApi;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    public ResponseEntity<List<Measurement>> getMeasurements() {
        List<Measurement> measurements = measurementService.getAll(PageRequest.of(0, 100));
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }


    @RequestMapping(value = RestApi.AVG_MEASUREMENT_PATH, method = RequestMethod.GET)
    public ResponseEntity<MeasurementAverage> getAvgMeasurement(@PathVariable("town_id") long id, @RequestParam(value = "from", required = false) String from) {

        if (StringUtils.isEmpty(from)) {
            from = RestApi.Interval.DEFAULT;
        }

        MeasurementAverage measurement;
        switch (from) {
            case RestApi.Interval.DAY:
                measurement = measurementService.getAverageForDay(id);
                break;
            case RestApi.Interval.WEEK:
                measurement = measurementService.getAverageForWeek(id);
                break;
            case RestApi.Interval.TWO_WEEKS:
                measurement = measurementService.getAverageForTwoWeeks(id);
                break;
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(measurement, HttpStatus.OK);
    }

    @RequestMapping(value = RestApi.MEASUREMENTS_PATH, method = RequestMethod.POST)
    public ResponseEntity<Measurement> addMeasurement(@RequestBody Measurement measurement) {
        measurementService.save(measurement);
        return new ResponseEntity<>(measurement, HttpStatus.OK);
    }

    @RequestMapping(value = RestApi.MEASUREMENT_PATH, method = RequestMethod.POST)
    public ResponseEntity<Measurement> updateMeasurement(@RequestBody Measurement measurement) {
        Optional<Measurement> requestedMeasurement = measurementService.get(measurement.getId());
        if (requestedMeasurement.isPresent()) {
            Measurement updatedMeasurement = measurementService.save(measurement);
            return new ResponseEntity<>(updatedMeasurement, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @RequestMapping(value = RestApi.MEASUREMENT_PATH, method = RequestMethod.GET)
    public ResponseEntity<Measurement> getMeasurement(@PathVariable("id") String id) {
        Optional<Measurement> measurement = measurementService.get(id);
        if (!measurement.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(measurement.get(), HttpStatus.OK);

    }

    @RequestMapping(value = RestApi.ACTUAL_MEASUREMENT_PATH, method = RequestMethod.GET)
    public ResponseEntity<MeasurementData> getActualMeasurement(@PathVariable("town_id") long id) {
        MeasurementData measurement = measurementService.findActualWeatherDataForTown(id);
        if (measurement == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(measurement, HttpStatus.OK);

    }


    @RequestMapping(value = RestApi.MEASUREMENT_PATH, method = RequestMethod.DELETE)
    public ResponseEntity deleteMeasurement(@PathVariable("id") String id) {
        Optional<Measurement> measurement = measurementService.get(id);
        if (!measurement.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            measurementService.delete(measurement.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
