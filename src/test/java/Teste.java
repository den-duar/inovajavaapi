import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class Teste {

    @BeforeEach
    public void before(){
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    public void teste01(){
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                    .get("/api/users/2")
                .then()
                    .statusCode(200);
    }
}
