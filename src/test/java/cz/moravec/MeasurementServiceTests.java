package cz.moravec;

import cz.moravec.model.Measurement;
import cz.moravec.model.Measurement;
import cz.moravec.repository.MeasurementRepository;
import cz.moravec.service.MeasurementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class MeasurementServiceTests {

    @Autowired
    private MeasurementService measurementService;


    @Test
    public void createMeasurementTest() {
        Measurement Measurement = createMeasurement();
        measurementService.save(Measurement);
        assertTrue("Measurement creation has failed", measurementService.exists(Measurement.getId()));
    }

    @Test
    public void deleteMeasurementTest() {

        Measurement newMeasurement = createMeasurement();
        Measurement measurement = measurementService.save(newMeasurement);
        long count1 = measurementService.getCount();

        boolean result = measurementService.delete(measurement);
        long count2 = measurementService.getCount();
        assertTrue("Delete method should return true", result);
        assertEquals("One measurement should be deleted", count1, count2 + 1);
    }

    @Test
    public void deleteMeasurementsTest() {

        List<Measurement> measurements = createMeasurements();
        measurementService.save(measurements.get(0));
        measurementService.save(measurements.get(1));
        boolean result = measurementService.deleteAll();
        assertTrue("All measurements should be deleted", result);
    }

    @Test
    public void createMeasurementsTest() {

        measurementService.deleteAll();
        List<Measurement> measurements = createMeasurements();
        measurementService.save(measurements);

        assertEquals("3 measurements should be created", 3, measurementService.getCount());
    }

    @Test
    public void getMeasurementsTest() {

        Measurement measurement = createMeasurement();
        measurementService.save(measurement);
        long count = measurementService.getCount();
        assertTrue("There should be measurements in database!", count >= 1);
        Iterable<Measurement> all = measurementService.getAll(PageRequest.of(0,10));
        Optional<Measurement> measurementFromDb = measurementService.get(all.iterator().next().getId());
        assertTrue("Retrieved Measurement is null", measurementFromDb.isPresent());
        assertEquals("Retrieved Measurement should match created Measurement.", measurement.getTemperature(), measurementFromDb.get().getTemperature(), 0.0);

    }


    @Test
    public void updateCountyTest() {
        Measurement measurement = createMeasurement();
        measurementService.save(measurement);
        Iterable<Measurement> measurements = measurementService.getAll(PageRequest.of(0,10));
        Measurement measurementFromDb = measurements.iterator().next();
        float value = 12.5781615f;
        measurementFromDb.setPressure(value);
        measurementService.save(measurementFromDb);

        Optional<Measurement> measurementDb = measurementService.get(measurementFromDb.getId());
        assertTrue("Retrieved measurement is null", measurementDb.isPresent());
        assertEquals("Update did not work, measurements are not same", measurementDb.get().getPressure(), value,0.0);
    }

    @Test
    public void getMeasurementByIdTest() {
        Measurement measurement = createMeasurement();
        measurementService.save(measurement);
        Iterable<Measurement> measurements = measurementService.getAll(PageRequest.of(0,10));
        Measurement measurementFromDb = measurements.iterator().next();
        Optional<Measurement> retrievedMeasurementById = measurementService.get(measurementFromDb.getId());
        assertTrue("Retrieved Measurement is null", retrievedMeasurementById.isPresent());
        assertEquals("Measurement by ID should match Measurement from list.", measurementFromDb.getTemperature(), retrievedMeasurementById.get().getTemperature(),0.0);

    }

    //support methods
    private Measurement createMeasurement() {
        return new Measurement(10f, 20f, 30f);
    }

    private List<Measurement> createMeasurements() {
        List<Measurement> list = new ArrayList<>();
        Measurement measurement1 = new Measurement(10f, 20f, 30f);
        Measurement measurement2 = new Measurement(0f, 15f, 17f);
        Measurement measurement3 = new Measurement(-10f, 2f, 3f);
        list.add(measurement1);
        list.add(measurement2);
        list.add(measurement3);
        return list;
    }
}
