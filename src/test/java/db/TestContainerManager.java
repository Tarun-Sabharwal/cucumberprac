package db;

import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestContainerManager {

    // declaring a postgresql container object that will be shared across all tests
    // represents the database running inside a docker container
    public static PostgreSQLContainer<?> postgres;

    static {
        System.out.println("Starting TestContainer...");

        // uses docker image , creates a container internally and starts it
        postgres = new PostgreSQLContainer<>("postgres:15");
        // starts the container automatically when the class is loaded
        postgres.start();
    }

    // method to connect to container database and return a Connection object
    public static Connection getConnection() throws Exception {

        System.out.println("Using JDBC URL: " + postgres.getJdbcUrl());
        System.out.println("Username: " + postgres.getUsername());

        // returns a connection to the database running inside the container
        return DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
    }
}





















/*package db;



import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestContainerManager {

    //  Define a static PostgreSQLContainer that will be shared across all tests
    public static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    static {

        System.out.println("DOCKER_HOST = " + System.getenv("DOCKER_HOST"));
        System.out.println("Trying to start container...");

        postgres.start(); //  auto container start
    }

    // utility method to get a Connection object to the TestContainer DB
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
    }
}*/