package stepdefinitions;

import db.TestContainerManager;
import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import model.Car;
import service.CarValidationService;

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class  CarSteps {

    List<Car> cars;
    List<Car> validCars = new ArrayList<>();
    Connection con;

    @DataTableType
    public Car carEntry(Map<String, String> row) {

        Car c = new Car();
        c.setId(Integer.parseInt(row.get("id")));
        c.setName(row.get("name"));
        c.setCompany(row.get("company"));
        c.setPrice(Integer.parseInt(row.get("price")));

        return c;
    }

    @Given("following car records")
    public void loadCars(DataTable table) throws Exception {

        cars = table.asList(Car.class);

        // calls test container manager class  and connects to the container database
        con = TestContainerManager.getConnection();

        // create table inside the container database.
        // statement is used to execute sql queries
        //stmt is an object of Statement class which is used to execute sql queries
        Statement stmt = con.createStatement();

        stmt.execute("CREATE TABLE IF NOT EXISTS cars(id INT, name TEXT, company TEXT, price INT)");

        System.out.println("✅ Table created inside container");
    }

    @When("valid cars are inserted")
    public void insertCars() throws Exception {

        PreparedStatement ps =
                con.prepareStatement("INSERT INTO cars VALUES (?, ?, ?, ?)");

        for (Car c : cars) {

            if (CarValidationService.isValid(c)) {

                validCars.add(c);

                ps.setInt(1, c.getId());
                ps.setString(2, c.getName());
                ps.setString(3, c.getCompany());
                ps.setInt(4, c.getPrice());

                ps.executeUpdate();

                System.out.println("✅ Inserted: " + c.getName());

            } else {
                System.out.println("❌ Skipped: " + c.getName());
            }
        }
    }

    @Then("only valid cars should exist in database")
    public void validateCars() throws Exception {

        ResultSet rs = con.createStatement()
                .executeQuery("SELECT * FROM cars");

        int count = 0;

        System.out.println("\n📊 Data in DB:");

        while (rs.next()) {
            count++;
            System.out.println(
                    rs.getInt("id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getString("company") + " | " +
                            rs.getInt("price"));
        }

        assertEquals(validCars.size(), count);

        System.out.println("\n✅ Assertion Passed");
    }
}