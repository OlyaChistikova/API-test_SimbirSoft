package tests;

import org.testng.annotations.Test;
import pojo.GetEntity;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class GetEntityTest extends BaseTest{

    @Test
    public void testGetEntityById(){
        GetEntity getEntityId = GetEntity.builder().build();
        given()
                .when()
                .get("api/get/4")
                .then()
                .statusCode(200)
                .body("addition.additional_info", equalTo(getEntityId.getAdditional_info()))
                .body("title", equalTo(getEntityId.getTitle()));
    }
}
