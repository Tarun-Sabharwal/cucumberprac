package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestContainerUtil {

    public static Connection getConnection() throws Exception {

// connects java to the PostgreSQL TestContainer running on localhost:5435 with credentials test/test
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5435/testdb",
                "test",
                "test"
        );

    }
}