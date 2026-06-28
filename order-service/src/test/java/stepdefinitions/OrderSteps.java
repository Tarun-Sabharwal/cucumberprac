package stepdefinitions;

import model.InventoryRequest;
import model.OrderRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;

public class OrderSteps {

    Response response;
    String productName;

    @Given("inventory has stock for {string} with quantity {int}")
    public void inventory_has_stock(String name, int stock) {

        // unique name per run so leftover rows from earlier test runs never collide
        productName = name + "-" + System.currentTimeMillis();

        RestAssured.baseURI = "http://localhost:8082";

        InventoryRequest item = new InventoryRequest();
        item.setName(productName);
        item.setStock(stock);

        RestAssured
                .given()
                .contentType("application/json")
                .body(item)
                .when()
                .post("/api/inventory")
                .then()
                .statusCode(201);
    }

    @When("I place an order for {string} with quantity {int}")
    public void place_order(String product, int quantity) {

        RestAssured.baseURI = "http://localhost:8081";

        OrderRequest order = new OrderRequest();
        order.setProduct(productName);
        order.setQuantity(quantity);

        response = RestAssured
                .given()
                .contentType("application/json")
                .body(order)
                .when()
                .post("/api/orders")
                .then()
                .extract().response();
    }

    @Then("the order should be created with status {string}")
    public void order_created_with_status(String expectedStatus) {

        int statusCode = response.getStatusCode();
        String status = response.jsonPath().getString("status");

        assertEquals(201, statusCode);
        assertEquals(expectedStatus, status);

        System.out.println("---------- Order created with status: " + status);
    }
}
