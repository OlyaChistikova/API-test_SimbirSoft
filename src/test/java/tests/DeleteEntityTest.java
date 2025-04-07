package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;


public class DeleteEntityTest extends BaseTest{

    @Test
    public void testDeleteEntityById() throws IOException {

        String jsonBody = new String(Files.readAllBytes(Paths.get("src/test/resources/json/userDeleteBody.json")));
        //Создание новой и получение entityId
        Response createEntity = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post("/api/create")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        //Получение entityId
        String entityId = createEntity.asString();

        //Удаляем, созданную сущность по id
        given()
                .when()
                .log().all()
                .delete("/api/delete/" + entityId)
                .then()
                .log().all()
                .statusCode(204);

        //Проверка, что сущность была удалена
        given()
                .when()
                .get("/api/get/" + entityId)
                .then()
                .log().all()
                .statusCode(500);

    }
}
