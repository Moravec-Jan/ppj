package cz.moravec.data;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Transactional
public class CountryDao {

    public static final String TABLE_NAME = "country";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";

    @Autowired
    private SessionFactory sessionFactory;

    public Session session() {
        return sessionFactory.getCurrentSession();
    }

    public List<Country> getCountries() {
        Criteria crit = session().createCriteria(Country.class);
        return crit.list();
    }

//    public boolean update(Country country) {
//        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
//                country);
//
//        return jdbc.update("UPDATE " + TABLE_NAME + " SET " + NAME_ATTRIBUTE + "=:name WHERE " + ID_ATTRIBUTE + "=:id", params) == 1;
//    }
//
//    public boolean create(Country country) {
//
//        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
//                country);
//
//        return jdbc
//                .update("INSERT INTO " + TABLE_NAME + " (" + NAME_ATTRIBUTE + ") VALUES (:name)",
//                        params) == 1;
//    }
//
//    @Transactional
//    public int[] create(List<Country> countries) {
//
//        SqlParameterSource[] params = SqlParameterSourceUtils
//                .createBatch(countries.toArray());
//
//        return jdbc
//                .batchUpdate("INSERT INTO " + TABLE_NAME + " (" + NAME_ATTRIBUTE + ") VALUES (:name)", params);
//    }
//
//    public boolean delete(int id) {
//        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
//
//        return jdbc.update("DELETE FROM " + TABLE_NAME + " WHERE " + ID_ATTRIBUTE + "=:id", params) == 1;
//    }
//
//
//    public Country getCountry(int id) {
//        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
//        return jdbc.queryForObject("SELECT " + ID_ATTRIBUTE + "," + NAME_ATTRIBUTE + " FROM " + TABLE_NAME+ " WHERE "+ ID_ATTRIBUTE + "=:id",params,BeanPropertyRowMapper.newInstance(Country.class));
//    }
//
//
//    public void deleteCountries() {
//        jdbc.getJdbcOperations().execute("DELETE FROM " + TABLE_NAME);
//    }

}
