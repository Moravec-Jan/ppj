package cz.moravec.provisioning;

import cz.moravec.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.util.List;

public class MySqlProvisioner implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    private DataSource dataSource;

    private final static String SELECT_TABLE_NAMES_QUERY = "SELECT TABLE_NAME FROM  INFORMATION_SCHEMA.TABLES";

    public void doProvisionIfNeeded() {

        List<String> allTables;

        allTables = namedParameterJdbcOperations.getJdbcOperations().queryForList(SELECT_TABLE_NAMES_QUERY, String.class);
        if (!allTables.contains("COUNTRY")) {
            log.warn("DB MySqlProvisioner: No tables exist and will be created");
            createDb();
            allTables = namedParameterJdbcOperations.getJdbcOperations().queryForList(SELECT_TABLE_NAMES_QUERY, String.class);
            System.out.println(allTables);
        } else
            log.info("DB MySqlProvisioner: Table COUNTRY exists, all existing tables: " + allTables);
    }

    private void createDb() {
        Resource rc = new ClassPathResource("create_tables.hsql");
        try {
            ScriptUtils.executeSqlScript(dataSource.getConnection(), rc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() {
        doProvisionIfNeeded();
    }
}
