package cz.moravec.service;

import cz.moravec.model.Measurement;
import cz.moravec.model.Measurement;
import cz.moravec.repository.CountryRepository;
import cz.moravec.repository.MeasurementRepository;
import cz.moravec.repository.MeasurementRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {

    private final MeasurementRepository repository;

    @Autowired
    public MeasurementService(MeasurementRepository repository) {
        this.repository = repository;
    }


    public Optional<Measurement> get(ObjectId id) {
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

    public boolean exists(ObjectId id) {
        return repository.existsById(id);
    }

    public long getCount() {
        return repository.count();
    }

    public boolean delete(Measurement Measurement) {
        repository.delete(Measurement);
        return !repository.existsById(Measurement.getId());
    }

    public boolean deleteAll() {
        repository.deleteAll();
        return repository.count() == 0;
    }
}
