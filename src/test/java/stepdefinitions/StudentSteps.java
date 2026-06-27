package stepdefinitions;

import io.cucumber.java.en.*;
import io.cucumber.java.DataTableType;
import io.cucumber.datatable.DataTable;

import model.Student;
import db.DBUtil;
import service.ValidationService;

import java.sql.ResultSet;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class StudentSteps {

    List<Student> students;
    List<Student> validStudents = new ArrayList<>();
    List<Student> highScorers = new ArrayList<>();

    @DataTableType // converts each row of DataTable into Student object
    public Student studentEntry(Map<String, String> row) {

        Student s = new Student();
        s.setId(Integer.parseInt(row.get("id")));
        s.setName(row.get("name"));
        s.setScore(Integer.parseInt(row.get("score")));

        return s;
    }

    @Given("following students exist")
    public void students_exist(DataTable table) {
        System.out.println("================STUDENT VALIDATION + DB==============================");
        students = table.asList(Student.class);
    }

    //  VALIDATE + INSERT + FETCH
    @When("valid students are stored in database")
    public void validate_and_store() throws Exception {

        DBUtil.createTable();// create a table  and clean old data

        for (Student s : students) {

            //  VALIDATION (YOUR IDEA)
            if (ValidationService.isValid(s)) {

                validStudents.add(s); // store valid students in a list

                DBUtil.insert(s.getId(), s.getName(), s.getScore()); // insert valid students into DB
            }
        }

        //  FETCH FROM DB
        ResultSet rs = DBUtil.fetchAll(); // fetch all records from DB

        while (rs.next()) {

            int score = rs.getInt("score"); // get score from DB

            if (score > 70) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setScore(score);

                highScorers.add(s);
            }
        } //
    }

    @Then("only valid students should be stored")
    public void validate_valid_students() {

        //  valid: Tarun, Ankit, Neha → 3
        assertEquals(5, validStudents.size());
    }

    @Then("high scoring students should be validated")
    public void validate_scores() {

        //  high scorers: Tarun, Neha → 2
        assertEquals(3, highScorers.size());

        boolean allHigh = highScorers.stream()
                .allMatch(s -> s.getScore() > 70);

        assertTrue(allHigh);

        System.out.println("Validation + DB + Assertions working ✅");
    }
}