package api;
import model.InventoryRequest;
import model.OrderRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


// this method will test the order-service API by first adding stock to the inventory-service 
// and then creating an order for that product. It will validate the response to ensure that the order was created successfully.
public class OrderApiTest {

    @Test
    public void testCreateOrderWithObject() {

        // first add stock in inventory-service so the order has enough to deduct
        RestAssured.baseURI = "http://localhost:8082";

        InventoryRequest item = new InventoryRequest();
        item.setName("Camera");
        item.setStock(100);

        RestAssured
                .given()
                .contentType("application/json")
                .body(item)
                .when()
                .post("/api/inventory")
                .then()
                .statusCode(201);

        // now create an order for that same product in order-service
        RestAssured.baseURI = "http://localhost:8081";

        OrderRequest order = new OrderRequest();
        order.setProduct("Camera");
        order.setQuantity(5);

        Response res = RestAssured
                .given()
                .contentType("application/json")
                .body(order)
                .when()
                .post("/api/orders")
                .then()
                .statusCode(201)
                .extract().response();

        // validate response
        String product = res.jsonPath().getString("product");
        String status = res.jsonPath().getString("status");

        assertEquals("Camera", product);
        assertEquals("PENDING", status);

        System.out.println("---------- Order API working");
    }
}
