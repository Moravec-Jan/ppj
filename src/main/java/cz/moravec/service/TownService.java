package cz.moravec.service;

import cz.moravec.model.Town;
import cz.moravec.repository.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TownService {


    private final TownRepository repository;

    @Autowired
    public TownService(TownRepository repository) {
        this.repository = repository;
    }

    public Optional<Town> get(long id) {
        return repository.findById(id);
    }

    public List<Town> getAll(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    public Town save(Town Town) {
        return repository.save(Town);
    }

    public void save(List<Town> countries) {
        repository.saveAll(countries);
    }

    public boolean exists(long id) {
        return repository.existsById(id);
    }

    public void delete(Town Town) {
        repository.delete(Town);
    }
}
