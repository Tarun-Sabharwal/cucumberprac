package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BasicApiTest {

    @Test
    public void testBasicGet() {

        RestAssured.baseURI="https://jsonplaceholder.typicode.com";

        Response res= RestAssured
                .given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200).extract().response();
        /*Response res= RestAssured
                .given().log().all()
                .when()
                .get("/posts")
                .then().log().all()
                .statusCode(200).extract().response();*/

        int code=res.getStatusCode();
        System.out.println("Status code is: "+code);

         String body=res.getBody().asString();
        // System.out.println("Response body is: "+body);

        int id1=res.jsonPath().getInt("[0].id");
        System.out.println("Id of first post is: "+id1);

        String title1 = res.jsonPath().getString("[0].title");
        System.out.println("Title of first post is: "+title1);

        Response res2= RestAssured
                .given()
                .when()
                .get("/posts/" + id1)
                .then()
                .statusCode(200).extract().response();

        int id2=res2.jsonPath().getInt("id");
        System.out.println("Id of second post is: "+id2);

        assertEquals(id1,id2);


        System.out.println("---------Basic API working");
    }
}
