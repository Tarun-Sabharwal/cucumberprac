package api;

import io.restassured.RestAssured;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class GetApiTest {

    @Test
    public void testGetResponse() {

        RestAssured
                .given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(200)
                .body("[0].id", equalTo(1))
                .log().all();

        System.out.println("----------GET API validated");
    }
}