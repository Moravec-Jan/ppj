package cz.moravec.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CountryDao {

    public static final String TABLE_NAME = "country";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";

    @Autowired
    private NamedParameterJdbcOperations jdbc;

    public List<Country> getCountries() {

        return jdbc.query("SELECT " + ID_ATTRIBUTE + "," + NAME_ATTRIBUTE + " FROM " + TABLE_NAME, BeanPropertyRowMapper.newInstance(Country.class));
    }

    public boolean update(Country country) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
                country);

        return jdbc.update("UPDATE " + TABLE_NAME + " SET " + NAME_ATTRIBUTE + "=:name WHERE " + ID_ATTRIBUTE + "=:id", params) == 1;
    }

    public boolean create(Country country) {

        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
                country);

        return jdbc
                .update("INSERT INTO " + TABLE_NAME + " (" + NAME_ATTRIBUTE + ") VALUES (:name)",
                        params) == 1;
    }

    @Transactional
    public int[] create(List<Country> countries) {

        SqlParameterSource[] params = SqlParameterSourceUtils
                .createBatch(countries.toArray());

        return jdbc
                .batchUpdate("INSERT INTO " + TABLE_NAME + " (" + NAME_ATTRIBUTE + ") VALUES (:name)", params);
    }

    public boolean delete(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        return jdbc.update("DELETE FROM " + TABLE_NAME + " WHERE " + ID_ATTRIBUTE + "=:id", params) == 1;
    }


    public Country getCountry(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbc.queryForObject("SELECT " + ID_ATTRIBUTE + "," + NAME_ATTRIBUTE + " FROM " + TABLE_NAME+ " WHERE "+ ID_ATTRIBUTE + "=:id",params,BeanPropertyRowMapper.newInstance(Country.class));
    }


    public void deleteCountries() {
        jdbc.getJdbcOperations().execute("DELETE FROM " + TABLE_NAME);
    }

}
