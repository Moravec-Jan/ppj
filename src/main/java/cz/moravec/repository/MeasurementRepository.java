package cz.moravec.repository;

import cz.moravec.model.Measurement;
import cz.moravec.model.projections.MeasurementData;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MeasurementRepository extends MongoRepository<Measurement, String> {
    @SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection") // using concrete class instead of interface because retrofit needs implementation
    MeasurementData findFirstByTownIdOrderByIdDesc(long townId);
}
