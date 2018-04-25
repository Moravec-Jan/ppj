package cz.moravec.repository;

import cz.moravec.model.Town;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends CrudRepository<Town,Integer> {
}
