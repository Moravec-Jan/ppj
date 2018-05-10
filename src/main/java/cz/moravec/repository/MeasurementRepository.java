package cz.moravec.repository;

import cz.moravec.model.Measurement;
import cz.moravec.model.projections.MeasurementData;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


public interface MeasurementRepository extends MongoRepository<Measurement, String> {
    MeasurementData findFirstByTownIdOrderByIdDesc(long townId);
}
