package stepdefinitions;

import io.cucumber.java.en.*;
import io.cucumber.java.DataTableType;
import io.cucumber.datatable.DataTable;
import model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class OrderSteps {

    List<Order> orders;

    // ✅ Fake DB
    List<Order> database = new ArrayList<>();

    // ✅ Mapping
    @DataTableType
    public Order orderEntry(Map<String, String> row) {
        //System.out.println("========================Order===============================");

        Order o = new Order();
        o.setOrder_id(row.get("order_id"));
        o.setProduct(row.get("product"));
        o.setQuantity(Integer.parseInt(row.get("quantity")));

        return o;
    }

    // ✅ GIVEN → store in DB
    @Given("following orders exist")
    public void orders_exist(DataTable table) {

        orders = table.asList(Order.class);

        database.clear();
        database.addAll(orders);
    }

    @And("user is authorized")
    public void user_authorized() {
        System.out.println("User is authorized");
    }

    // ✅ WHEN → process data
    @When("I process the orders")
    public void process_orders() {

        for (Order o : database) {

            if (o.getQuantity() > 15) {
                o.setProduct(o.getProduct() + " (BULK)");
            }

            if (o.getQuantity() < 1) {
                o.setProduct(o.getProduct() + " (OUT OF STOCK)");
            }
        }
    }

    // ✅ THEN → ASSERTIONS (REAL TESTING)
    @Then("orders should be processed successfully")
    public void verify_orders() {

        // ✅ check DB not empty
        assertFalse(database.isEmpty());

        // ✅ check size
        assertEquals(6, database.size());

        // ✅ check BULK exists
        boolean bulkFound = database.stream()
                .anyMatch(o -> o.getProduct().contains("BULK"));
        assertTrue(bulkFound);

        //  check OUT OF STOCK exists
        boolean outOfStock = database.stream()
                .anyMatch(o -> o.getProduct().contains("OUT OF STOCK"));
        assertTrue(outOfStock);

        System.out.println("All assertions passed ");
    }
}
















/*package stepdefinitions;



import io.cucumber.java.en.*;
import io.cucumber.java.DataTableType;
import io.cucumber.datatable.DataTable;
import model.Order;

import java.util.List;
import java.util.Map;

public class OrderSteps {

    List<Order> orders;

    @DataTableType
    public Order orderEntry(Map<String, String> row) {

        Order o = new Order();
        o.setOrder_id(row.get("order_id"));
        o.setProduct(row.get("product"));
        o.setQuantity(Integer.parseInt(row.get("quantity")));

        return o;
    }

    @Given("following orders exist")
    public void orders_exist(DataTable table) {
        orders = table.asList(Order.class);

        System.out.println("================ORDER==============================");
        System.out.println("=== GIVEN ===");
        for (Order o : orders) {
            System.out.println(o.getOrder_id() + " - " + o.getProduct());
        }
    }

    @And("user is authorized")
    public void user_authorized() {
        System.out.println("=== AND === User authorized");
    }

    @When("I process the orders")
    public void process_orders() {

        System.out.println("=== WHEN ===");

        for (Order o : orders) {

            System.out.println("Processing order "
                    + o.getOrder_id() + " ---> " + o.getProduct());

            if (o.getQuantity() > 15) {
                System.out.println("Bulk order: "
                        + o.getOrder_id() + " --- "
                        + o.getProduct() + " (" + o.getQuantity() + " items)");
            }

            if (o.getQuantity() < 1) {
                System.out.println("Order " + o.getProduct() + " is out of stock!");
            }
        }
    }

    @Then("orders should be processed successfully")
    public void verify_orders() {

        System.out.println("=== THEN ===");

        if (orders != null && !orders.isEmpty()) {
            System.out.println("Total processed: " + orders.size());
        } else {
            System.out.println("No orders found!");
        }
    }
}*/