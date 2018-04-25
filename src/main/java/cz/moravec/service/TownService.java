package cz.moravec.service;

import cz.moravec.model.Town;
import cz.moravec.repository.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Profile("!mongo_test")
@Service
public class TownService {


    private final TownRepository repository;

    @Autowired
    public TownService(TownRepository repository) {
        this.repository = repository;
    }

    public Optional<Town> get(int id) {
        return repository.findById(id);
    }

    public Iterable<Town> getAll() {
        return repository.findAll();
    }

    public Town save(Town Town) {
        return repository.save(Town);
    }

    public void save(List<Town> countries) {
        repository.saveAll(countries);
    }

    public boolean exists(int id) {
        return repository.existsById(id);
    }

    public long getCount() {
        return repository.count();
    }

    public boolean delete(Town Town) {
        repository.delete(Town);
        return !repository.existsById(Town.getId());
    }

    public boolean deleteAll() {
        repository.deleteAll();
        return repository.count() == 0;
    }
}
