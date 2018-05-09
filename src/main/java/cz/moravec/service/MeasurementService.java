package cz.moravec.service;

import cz.moravec.model.Measurement;
import cz.moravec.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;

@Service
public class MeasurementService {

    private final MeasurementRepository repository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public MeasurementService(MeasurementRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }


    public Optional<Measurement> get(String id) {
        return repository.findById(id);
    }

    public List<Measurement> getAll(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    public Measurement save(Measurement Measurement) {
        return repository.save(Measurement);
    }

    public void save(List<Measurement> countries) {
        repository.saveAll(countries);
    }

    public boolean exists(String id) {
        return repository.existsById(id);
    }


    public long getCount() {
        return repository.count();
    }

    public boolean delete(Measurement Measurement) {
        repository.delete(Measurement);
        return !repository.existsById(Measurement.getId());
    }

    public Measurement getAverageForDay() {
        GroupOperation group = group()
                .avg(Measurement.HUMIDITY_NAME).as("Humidity average")
                .avg(Measurement.PRESSURE_NAME).as("Pressure average")
                .avg(Measurement.TEMPERATURE_NAME).as("Temperature average");
        MatchOperation matchStage = Aggregation.match(new Criteria(Measurement.CREATION_TIME_NAME).gt(getDateBeforeOneDay()));
        ProjectionOperation projectStage = Aggregation.project("Humidity average");
        //projectStage = projectStage.andExclude(Measurement.ID_NAME);
        Aggregation aggregation = Aggregation.newAggregation(matchStage);

        AggregationResults<Measurement> output
                = mongoTemplate.aggregate(aggregation, Measurement.COLLECTION_NAME, Measurement.class);
        List<Measurement> mappedResults = output.getMappedResults();
        return null;
    }

    private static Date getDateBeforeTwoWeeks() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -14);
        return calendar.getTime();
    }

    private static Date getDateBeforeOneDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    private static Date getDateBeforeWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        return calendar.getTime();
    }

    public Measurement getAverageForWeek() {
        return null;
    }

    public Measurement getAverageForTwoWeeks() {
        return null;
    }


    public boolean deleteAll() {
        repository.deleteAll();
        return repository.count() == 0;
    }
}
