package stepdefinitions;

import io.cucumber.java.en.*;
import io.cucumber.java.DataTableType;
import io.cucumber.datatable.DataTable;

import model.StudentTC;
import service.ValidationServiceTC;
import db.TestContainerUtil;

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTCSteps {



    static {
        //  FORCE correct timezone globally
        System.setProperty("user.timezone", "Asia/Kolkata");
    }

    List<StudentTC> students;
    List<StudentTC> validStudents = new ArrayList<>();

    Connection con; // will hold our DB connection

    //  Mapping DataTable → Object
    @DataTableType
    public StudentTC studentEntry(Map<String, String> row) {

        StudentTC s = new StudentTC();
        s.setId(Integer.parseInt(row.get("id")));
        s.setName(row.get("name"));
        s.setScore(Integer.parseInt(row.get("score")));

        return s;
    }

    //  GIVEN
    @Given("following student records")
    public void load_students(DataTable table) throws Exception {

        System.out.println("\n========== GIVEN ==========");

        students = table.asList(StudentTC.class); // load DataTable into List<StudentTC>

        for (StudentTC s : students) {
            System.out.println("Input Data → ID: " + s.getId()
                    + " | Name: " + s.getName()
                    + " | Score: " + s.getScore());
        }

        con = TestContainerUtil.getConnection(); // connect to PostgreSQL TestContainer

        System.out.println(" Connected to PostgreSQL TestContainer");

        Statement stmt = con.createStatement(); // create a Statement object to execute SQL commands

        stmt.execute("DROP TABLE IF EXISTS students");
        stmt.execute("CREATE TABLE students(id INT, name TEXT, score INT)");

        System.out.println(" Table created");
    }

    //  WHEN
    @When("valid students are inserted into postgres")
    public void insert_students() throws Exception {

        System.out.println("\n========== WHEN ==========");

        // Use PreparedStatement to safely insert data into DB
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO students VALUES (?, ?, ?)");

        for (StudentTC s : students) {

            System.out.println("\nChecking Student → " + s.getName());

            if (ValidationServiceTC.isValid(s)) {

                System.out.println(" VALID → inserting into DB");

                validStudents.add(s);

                ps.setInt(1, s.getId());
                ps.setString(2, s.getName());
                ps.setInt(3, s.getScore());
                ps.executeUpdate();

            } else {

                System.out.println(" INVALID → skipping (Score: " + s.getScore() + ")");
            }
        }
    }

    // THEN
    @Then("only valid students should exist in database")
    public void validate_db() throws Exception {

        System.out.println("\n========== THEN ==========");

        // Query DB to get all students
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM students");

        List<StudentTC> dbList = new ArrayList<>();

        System.out.println("\n Data inside PostgreSQL:");

        while (rs.next()) {

            StudentTC s = new StudentTC();
            s.setId(rs.getInt("id"));
            s.setName(rs.getString("name"));
            s.setScore(rs.getInt("score"));

            dbList.add(s);

            System.out.println("DB Row → ID: " + s.getId()
                    + " | Name: " + s.getName()
                    + " | Score: " + s.getScore());
        }

        System.out.println("\n Total valid inserted: " + dbList.size());

        //  ASSERTIONS
        assertEquals(3, dbList.size());

        boolean allValid = dbList.stream()
                .allMatch(s -> s.getScore() >= 0 && s.getScore() <= 100);

        assertTrue(allValid);

        System.out.println("\n Assertions Passed");
        System.out.println(" TestContainers DB flow working correctly\n");
    }
}