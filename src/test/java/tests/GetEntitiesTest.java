package tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetEntitiesTest extends BaseTest{

    @Test
    public void testGetAllEntities(){
        given()
                .when()
                .get("/api/getAll")
                .then()
                .statusCode(200);
    }
}
