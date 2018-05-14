package cz.moravec.service;

import cz.moravec.model.Measurement;
import cz.moravec.model.Town;
import cz.moravec.model.projections.MeasurementAverage;
import cz.moravec.model.projections.MeasurementData;
import cz.moravec.repository.MeasurementRepository;
import cz.moravec.repository.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MeasurementService {
    private final TownRepository townRepository;
    private final MeasurementRepository repository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public MeasurementService(TownRepository townRepository, MeasurementRepository repository, MongoTemplate mongoTemplate) {
        this.townRepository = townRepository;
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }


    public Optional<Measurement> get(String id) {
        return repository.findById(id);
    }

    public List<Measurement> getAll(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    public MeasurementData findActualWeatherDataForTown(long townId) {
        return repository.findFirstByTownIdOrderByIdDesc(townId);
    }

    @Transactional(readOnly = true)
    public List<MeasurementData> findAllByCountry(long countryId) {
        List<Town> towns = townRepository.findAllByCountry_Id(countryId);
        List<MeasurementData> measurements = new ArrayList<>();
        towns.forEach((town) -> {
            MeasurementData measurement = findActualWeatherDataForTown(town.getId());
            if (measurement == null)
                return;
            measurement.setTownName(town.getName());
            measurements.add(measurement);
        });
        return measurements;
    }

    public Measurement save(Measurement Measurement) {
        return repository.save(Measurement);
    }

    public void save(List<Measurement> countries) {
        repository.saveAll(countries);
    }

    public long getCount() {
        return repository.count();
    }

    public boolean delete(Measurement Measurement) {
        repository.delete(Measurement);
        return !repository.existsById(Measurement.getId());
    }

    public MeasurementAverage getAverageForDay(long townId) {
        return getMeasurementAverageSince(townId, getDateBeforeOneDay());
    }


    public MeasurementAverage getAverageForWeek(long townId) {

        return getMeasurementAverageSince(townId, getDateBeforeWeek());
    }

    public MeasurementAverage getAverageForTwoWeeks(long townId) {
        return getMeasurementAverageSince(townId, getDateBeforeTwoWeeks());
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

    private static Date getDateBeforeTwoWeeks() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -14);
        return calendar.getTime();
    }


    private MeasurementAverage getMeasurementAverageSince(long townId, Date date) {
        GroupOperation group = Aggregation.group()
                .avg(Measurement.HUMIDITY_NAME).as(MeasurementAverage.HUMIDITY_AVERAGE)
                .avg(Measurement.PRESSURE_NAME).as(MeasurementAverage.PRESSURE_AVERAGE)
                .avg(Measurement.TEMPERATURE_NAME).as(MeasurementAverage.TEMPERATURE_AVERAGE);

        MatchOperation match = Aggregation.match(new Criteria(Measurement.CREATION_TIME_NAME).gt(date).and(Measurement.TOWN_ID_NAME).is(townId));
        ProjectionOperation projection = Aggregation.project(MeasurementAverage.TEMPERATURE_AVERAGE, MeasurementAverage.HUMIDITY_AVERAGE, MeasurementAverage.PRESSURE_AVERAGE);
        projection = projection.andExclude(Measurement.ID_NAME);
        Aggregation aggregation = Aggregation.newAggregation(match, group, projection);

        AggregationResults<MeasurementAverage> output = mongoTemplate.aggregate(aggregation, Measurement.COLLECTION_NAME, MeasurementAverage.class);
        return output.getUniqueMappedResult();
    }
}
