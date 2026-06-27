package api;
import model.Post;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PostApiTest {

    @Test
    public void testPostWithObject() {

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        // create a java object for the request body
        Post post = new Post();
        post.setTitle(" Tarun test");
        post.setBody("learning rest assured");
        post.setUserId(1);

        // sending request to the API with the request body
        Response res = RestAssured
                .given()
                .contentType("application/json")
                .body(post)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .extract().response();

        // validate response
        String title=res.jsonPath().getString("title");
        assertEquals(" Tarun test", title);

        System.out.println("---------- POST API working with object");


    }
}

// .header("Content-Type", "application/json")-> tell api -> data is in json format
/*
String requestBody = """
                {
                    "title": "test",
                    "body": "learning rest assured",
                    "userId": 1
                }
                """;

        // send POST request to the API with the request body
        RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(201)
                .log().all();

        System.out.println("---------------- POST API working");






  */