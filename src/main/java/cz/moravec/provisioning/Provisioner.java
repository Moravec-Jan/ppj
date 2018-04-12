package cz.moravec.provisioning;

import cz.moravec.Main;
import cz.moravec.data.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.util.List;

public class Provisioner {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    private DataSource dataSource;

    private final static String SELECT_TABLE_NAMES_QUERY = "SELECT TABLE_NAME FROM  INFORMATION_SCHEMA.TABLES";

    public void doProvision() {

        List<String> allTables;

        allTables = namedParameterJdbcOperations.getJdbcOperations().queryForList(SELECT_TABLE_NAMES_QUERY, String.class);
        if (!allTables.contains("COUNTRY")) {
            log.warn("DB Provisioner: No tables exist and will be created");
            createDb();
            allTables = namedParameterJdbcOperations.getJdbcOperations().queryForList(SELECT_TABLE_NAMES_QUERY, String.class);
            System.out.println(allTables);
        } else
            log.info("DB Provisioner: Table COUNTRY exists, all existing tables: " + allTables);
    }

    public void createDb() {
        Resource rc = new ClassPathResource("create_tables.hsql");
        try {
            ScriptUtils.executeSqlScript(dataSource.getConnection(), rc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
