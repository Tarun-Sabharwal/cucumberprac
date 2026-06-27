package api;

import io.restassured.RestAssured;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class ParamsApiTest {

    @Test
    public void testQueryParam() {

        RestAssured
                .given()
                .queryParam("userId", 1)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("---------------- Query Param working");
    }

    @Test
    public void testPathParam() {

        RestAssured
                .given()
                .pathParam("id", 1)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .log().all();

        System.out.println("------------------ Path Param working");
    }
}

// .queryParam("userId", 1) -> send query param to api
// for eg the url will become https://jsonplaceholder.typicode.com/posts?userId=1

