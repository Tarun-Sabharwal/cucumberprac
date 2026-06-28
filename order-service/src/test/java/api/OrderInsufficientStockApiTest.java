package api;
import model.InventoryRequest;
import model.OrderRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class OrderInsufficientStockApiTest {

    @Test
    public void testOrderRejectedWhenStockNotEnough() {

        // add inventory with limited stock
        RestAssured.baseURI = "http://localhost:8082";

        InventoryRequest item = new InventoryRequest();
        item.setName("Mouse");
        item.setStock(2);

        RestAssured
                .given()
                .contentType("application/json")
                .body(item)
                .when()
                .post("/api/inventory")
                .then()
                .statusCode(201);

        // try to order more than the available stock
        RestAssured.baseURI = "http://localhost:8081";

        OrderRequest order = new OrderRequest();
        order.setProduct("Mouse");
        order.setQuantity(5);

        Response res = RestAssured
                .given()
                .contentType("application/json")
                .body(order)
                .when()
                .post("/api/orders")
                .then()
                .extract().response();

        int statusCode = res.getStatusCode();
        System.out.println("Status code is: " + statusCode);

        assertEquals(400, statusCode);

        System.out.println("---------- Order correctly rejected due to insufficient stock");
    }
}
