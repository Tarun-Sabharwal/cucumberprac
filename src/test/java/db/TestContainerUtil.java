package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestContainerUtil {

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
                TestContainerManager.postgres.getJdbcUrl(),
                TestContainerManager.postgres.getUsername(),
                TestContainerManager.postgres.getPassword()
        );
    }
}