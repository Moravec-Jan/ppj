package cz.moravec;


import cz.moravec.model.Measurement;
import cz.moravec.model.projections.MeasurementAverage;
import cz.moravec.model.projections.MeasurementData;
import cz.moravec.service.MeasurementService;
import cz.moravec.web.RestApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {App.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles({"test"})
public class MeasurementServiceTests {


    @Autowired
    private MeasurementService measurementService;


    private final String TEST_URL = "http://localhost:8080";
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(TEST_URL).addConverterFactory(JacksonConverterFactory.create()).build();
    private RestApi restService = retrofit.create(RestApi.class);


    @Test
    public void createMeasurementTest() throws IOException {
        Measurement measurement = createMeasurement();
        long count1 = measurementService.getCount();
        Response<Measurement> response = restService.createMeasurement(measurement).execute();
        long count2 = measurementService.getCount();
        List<Measurement> all = measurementService.getAll(PageRequest.of(0, 30));
        Measurement last = all.get(all.size() - 1);
        assertEquals("Measurement has not been created", count1 + 1, count2);
        assertNotNull("Response is null", response.body());
        assertEquals("Created measurement does not equal requested", last.toString(), response.body().toString());
    }

    @Test
    public void deleteMeasurementTest() throws IOException {

        Measurement newMeasurement = createMeasurement();
        measurementService.save(newMeasurement);
        List<Measurement> all = measurementService.getAll(PageRequest.of(0, 10));
        Measurement someMeasurement = all.get(0);
        restService.deleteMeasurement(someMeasurement.getId()).execute();
        Optional<Measurement> measurement = measurementService.get(someMeasurement.getId());

        assertTrue("Measurement should be deleted", !measurement.isPresent());
    }

    @Test
    public void updateMeasurementTest() throws IOException {

        Measurement newMeasurement = createMeasurement();
        measurementService.save(newMeasurement);
        List<Measurement> all = measurementService.getAll(PageRequest.of(0, 10));
        Measurement someMeasurement = all.get(0);
        someMeasurement.setTemperature(-99.0D);
        Response<Measurement> response = restService.updateMeasurement(someMeasurement).execute();

        assertNotNull("Response is null!", response);
        Measurement result = response.body();
        assertNotNull("Resulted measurement is null", result);
        assertEquals("Measurement temperature should be updated", someMeasurement.getTemperature(), result.getTemperature(), 0D);
    }

    @Test
    public void getMeasurement() throws IOException {

        Measurement Measurement = createMeasurement();
        measurementService.save(Measurement);
        Iterable<Measurement> all = measurementService.getAll(PageRequest.of(0, 10));
        Measurement someMeasurement = all.iterator().next();
        Response<Measurement> execute = restService.getMeasurement(someMeasurement.getId()).execute();
        Measurement MeasurementFromRest = execute.body();
        assertNotNull("Retrieved measurement is null", MeasurementFromRest);
        assertEquals("Retrieved Measurement should match created measurement.", someMeasurement.toString(), MeasurementFromRest.toString());
    }

    @Test
    public void getMeasurements() throws IOException {

        List<Measurement> measurements = createMeasurements();
        measurementService.save(measurements);
        Response<List<Measurement>> execute = restService.getMeasurements().execute();
        List<Measurement> result = execute.body();
        assertNotNull("Result is null!", result);
        List<Measurement> all = measurementService.getAll(PageRequest.of(0, 100));
        assertEquals("Result should have same number of items!", result.size(), all.size());
    }

    @Test
    public void getActualMeasurement() throws IOException {

        List<Measurement> measurements = createMeasurements();
        measurementService.save(measurements);
        Response<MeasurementData> execute = restService.getActualMeasurement(1).execute();
        MeasurementData result = execute.body();
        assertNotNull("Result is null!", result);
        MeasurementData averageForDay = measurementService.findActualWeatherDataForTown(1);
        assertTrue("Wrong result", equals(result, averageForDay));
    }


    @Test
    public void getDefaultAvgMeasurement() throws IOException {

        List<Measurement> measurements = createMeasurements();
        measurementService.save(measurements);
        Response<MeasurementAverage> execute = restService.getAvgMeasurement(1).execute();
        MeasurementAverage result = execute.body();
        assertNotNull("Result is null!", result);
        MeasurementAverage averageForDay = measurementService.getAverageForDay(1);
        assertTrue("Wrong result", equals(result, averageForDay));
    }

    @Test
    public void getDayAvgMeasurement() throws IOException {

        List<Measurement> measurements = createMeasurements();
        measurementService.save(measurements);
        Response<MeasurementAverage> execute = restService.getAvgMeasurement(1, RestApi.Interval.DAY).execute();
        MeasurementAverage result = execute.body();
        assertNotNull("Result is null!", result);
        MeasurementAverage averageForDay = measurementService.getAverageForDay(1);
        assertTrue("Wrong result", equals(result, averageForDay));
    }

    @Test
    public void getWeekAvgMeasurement() throws IOException {

        List<Measurement> measurements = createMeasurements();
        measurementService.save(measurements);
        Response<MeasurementAverage> execute = restService.getAvgMeasurement(1, RestApi.Interval.WEEK).execute();
        MeasurementAverage result = execute.body();
        assertNotNull("Result is null!", result);
        MeasurementAverage averageForDay = measurementService.getAverageForWeek(1);
        assertTrue("Wrong result", equals(result, averageForDay));
    }

    @Test
    public void getTwoWeekAvgMeasurement() throws IOException {

        List<Measurement> measurements = createMeasurements();
        measurementService.save(measurements);
        Response<MeasurementAverage> execute = restService.getAvgMeasurement(1, RestApi.Interval.TWO_WEEKS).execute();
        MeasurementAverage result = execute.body();
        assertNotNull("Result is null!", result);
        MeasurementAverage averageForDay = measurementService.getAverageForTwoWeeks(1);
        assertTrue("Wrong result", equals(result, averageForDay));
    }


    //support methods
    private Measurement createMeasurement() {
        return new Measurement(1, 10f, 20f, 30f);
    }

    private List<Measurement> createMeasurements() {
        List<Measurement> list = new ArrayList<>();
        Measurement measurement1 = new Measurement(1, 10f, 20f, 30f);
        Measurement measurement2 = new Measurement(1, 0f, 15f, 17f);
        Measurement measurement3 = new Measurement(2, -10f, 2f, 3f);
        list.add(measurement1);
        list.add(measurement2);
        list.add(measurement3);
        return list;
    }

    private boolean equals(MeasurementAverage a, MeasurementAverage b) {
        return a.getHumidityAverage() == b.getHumidityAverage() &&
                a.getPressureAverage() == b.getPressureAverage() &&
                a.getTemperatureAverage() == b.getTemperatureAverage();
    }

    private boolean equals(MeasurementData a, MeasurementData b) {
        return a.getHumidity() == b.getHumidity() &&
                a.getPressure() == b.getPressure() &&
                a.getTemperature() == b.getTemperature() &&
                a.getTownId() == b.getTownId() &&
                a.getCreationTime().getTime() == b.getCreationTime().getTime();
    }
}
