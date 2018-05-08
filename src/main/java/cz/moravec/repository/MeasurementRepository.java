package cz.moravec.repository;

import cz.moravec.model.Measurement;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends MongoRepository<Measurement, String> {
}
