import Entity.UserPutResponse;
import Entity.UserRequest;
import Entity.UserResponse;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ResponseBody;
import org.apache.http.HttpStatus;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class TestApi {

    @BeforeAll
    @Test
    public static void setUri() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void methodGet() {
        File file = new File("src/test/resources/schemaUser.json");

        given()
            .contentType(ContentType.JSON)
        .when()
            .get("api/users/{id}", 2)
        .then().contentType(ContentType.JSON)
            .statusCode(HttpStatus.SC_OK)
                .and().body(JsonSchemaValidator.matchesJsonSchema(file));
    }

    @Test
    public void testeDennisGet(){
        RestAssured.given().contentType(ContentType.JSON)
                .when().get("api/users/{id}", 3)
                .then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testeGetPag(){
        RestAssured.given().contentType(ContentType.JSON)
                .when().get("api/users?page={id}",2)
                .then().statusCode(HttpStatus.SC_OK).contentType(ContentType.JSON)
                .log().body();
    }

    @Test
    public void createUser(){
        UserRequest userRequest = new UserRequest("Mr Anderson", "The chosen");
        UserResponse userResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userRequest)
                .when().post("api/users")
                .then().statusCode(HttpStatus.SC_CREATED)
                .and().extract().response().as(UserResponse.class);

        Assertions.assertEquals(userRequest.getName(), userResponse.getName());
        Assertions.assertEquals(userRequest.getJob(), userResponse.getJob());
        Assertions.assertNotNull(userResponse.getId());
        Assertions.assertNotNull(userResponse.getCreatedAt());
    }

    @Test
    public void putTest(){
        UserRequest userRequest = new UserRequest("Neo", "QA");
        UserPutResponse userPut = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userRequest)
                .when().put("api/users/{id}", 2)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().response().as(UserPutResponse.class);

        Assertions.assertEquals(userRequest.getName(), userPut.getName());
        Assertions.assertEquals(userRequest.getJob(), userPut.getJob());
        Assertions.assertNotNull(userPut.getUpdatedAt());
    }


}
