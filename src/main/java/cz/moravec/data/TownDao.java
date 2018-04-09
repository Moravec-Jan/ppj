package cz.moravec.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.List;

public class TownDao {
    public static final String TABLE_NAME = "town";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String COUNTRY_ATTRIBUTE = "country";

    @Autowired
    private NamedParameterJdbcOperations jdbc;

    public List<Town> getTowns(){
        return jdbc.query("SELECT " + ID_ATTRIBUTE + "," + NAME_ATTRIBUTE + " FROM " + TABLE_NAME, BeanPropertyRowMapper.newInstance(Town.class));
    }
}
