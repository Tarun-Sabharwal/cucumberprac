package stepdefinitions;

import java.util.*;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import model.Service;
import io.cucumber.java.DataTableType;
import java.util.List;

public class ServiceSteps {

    List<Service> services;
    @DataTableType
    public Service serviceEntry(Map<String, String> row) {
        Service s = new Service();
        s.setService_id(row.get("service_id"));
        s.setName(row.get("name"));
        return s;
    }




    @Given("following services exist")
    public void following_services_exist(DataTable table) {

        // ✅ KEY LINE (like your real project)
        services = table.asList(Service.class);

        System.out.println("=====================SERVICE=========================");
        System.out.println("=== GIVEN ===");

        for (Service s : services) {
            System.out.println("ID: " + s.getService_id());
            System.out.println("Name: " + s.getName());
        }
    }

    @And("system is ready")
    public void system_ready() {
        System.out.println("=== AND === System ready");
    }

    @When("I process services")
    public void process_services() {

        System.out.println("=== WHEN ===");

        for (Service s : services) {
            System.out.println("Processing: " + s.getName());
        }
    }

    @Then("services should be available")
    public void verify_services() {

        System.out.println("=== THEN ===");

        if (!services.isEmpty()) {
            System.out.println("Total services: " + services.size());
        }
    }
}