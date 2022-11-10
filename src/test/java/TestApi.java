import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class TestApi {


    @Test
    void methodGet() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("https://reqres.in/api/users/2")
        .then().contentType(ContentType.JSON)
            .statusCode(200);
    }
}
