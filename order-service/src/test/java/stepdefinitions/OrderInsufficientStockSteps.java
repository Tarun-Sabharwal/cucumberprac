package stepdefinitions;

import model.InventoryRequest;
import model.OrderRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;

public class OrderInsufficientStockSteps {

    Response response;

    @Given("inventory has limited stock for {string} with quantity {int}")
    public void inventory_has_limited_stock(String name, int stock) {

        RestAssured.baseURI = "http://localhost:8082";

        InventoryRequest item = new InventoryRequest();
        item.setName(name);
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

    @When("I attempt to place an order for {string} with quantity {int}")
    public void attempt_place_order(String product, int quantity) {

        RestAssured.baseURI = "http://localhost:8081";

        OrderRequest order = new OrderRequest();
        order.setProduct(product);
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

    @Then("the order request should be rejected with status code {int}")
    public void order_request_rejected(int expectedCode) {

        int statusCode = response.getStatusCode();
        System.out.println("Status code is: " + statusCode);

        assertEquals(expectedCode, statusCode);

        System.out.println("---------- Order correctly rejected due to insufficient stock");
    }
}
