package by.training.certificationCenter.service.impl;

import by.training.certificationCenter.dao.pool.ConnectionPoolWrapper;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.SqlScriptSource;
import com.wix.mysql.config.MysqldConfig;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.distribution.Version.v8_0_11;

public abstract class InitServiceTest {
    private static final String DATABASE_NAME = "certificationCenterTest";
    private static final int PORT = 3307;
    private static final String DATABASE_URL = String.format(
            "jdbc:mysql://localhost:%d/%s", PORT, DATABASE_NAME);
    private static final String USERNAME = "certificationCenterTest_user";
    private static final String PASSWORD = "";
    private static final SqlScriptSource[] INIT_SCRIPT_SOURCES
            = {classPathScript("sql/create_tables.sql"),
            classPathScript("sql/fill_organisation_table.sql"),
            classPathScript("sql/fill_user_table.sql")};
    private static Connection connection;
    private static EmbeddedMysql database;
    private static ConnectionPoolWrapper connectionWrapper;

    @BeforeSuite
    public void init() throws SQLException {
        MysqldConfig config = aMysqldConfig(v8_0_11).withPort(PORT)
                .withCharset(UTF8).withUser(USERNAME, PASSWORD).build();

        database = anEmbeddedMysql(config)
                .addSchema(DATABASE_NAME, INIT_SCRIPT_SOURCES).start();
        Properties properties = new Properties();
        properties.put("user", USERNAME);
        properties.put("password", PASSWORD);
        properties.put("characterEncoding", "UTF-8");
        connection = DriverManager.getConnection(DATABASE_URL, properties);
        connectionWrapper = new ConnectionPoolWrapper() {
            @Override
            public void initialPool() {
            }

            @Override
            public void closePool() {
            }

            @Override
            public Connection getConnection() {
                return connection;
            }

            @Override
            public void closeConnection(Connection connection) {
            }
        };
    }

    @BeforeMethod
    public void initMethod() {
        database.reloadSchema(DATABASE_NAME, INIT_SCRIPT_SOURCES);
    }

    @AfterSuite
    public void destroy() throws SQLException {
        connection.close();
        database.stop();
    }

    protected ConnectionPoolWrapper getConnectionWrapper() {
        return connectionWrapper;
    }

    protected List<String> executeScriptSource(
            final SqlScriptSource[] scriptSources) {
        return database.executeScripts(DATABASE_NAME, scriptSources);
    }
}
