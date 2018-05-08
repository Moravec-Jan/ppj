package cz.moravec.service;

import cz.moravec.model.Country;
import cz.moravec.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//@Profile("!mongo_test")
@Service
public class CountryService {


    private final CountryRepository repository;

    @Autowired
    public CountryService(CountryRepository repository) {
        this.repository = repository;
    }

    public Optional<Country> get(int id) {
        return repository.findById(id);
    }

    public List<Country> getAll(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    public Country save(Country country) {
        return repository.save(country);
    }

    public void save(List<Country> countries) {
        repository.saveAll(countries);
    }

    public boolean exists(int id) {
        return repository.existsById(id);
    }

    public long getCount() {
        return repository.count();
    }

    public boolean delete(Country country) {
        repository.delete(country);
        return !repository.existsById(country.getId());
    }

    public boolean deleteAll() {
        repository.deleteAll();
        return repository.count() == 0;
    }
}
