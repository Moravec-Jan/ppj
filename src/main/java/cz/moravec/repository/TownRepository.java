package cz.moravec.repository;

import cz.moravec.model.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TownRepository extends JpaRepository<Town,Long> {

    List<Town> findAllByCountry_Id(long id);
}
