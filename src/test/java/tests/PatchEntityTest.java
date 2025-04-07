package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class PatchEntityTest {


    @Test
    public void patchRequest() throws IOException {
        // Загрузка Json тела для обновления сущности
       String jsonBody = new String(Files.readAllBytes(Paths.get("src/test/resources/json/entityPatchBody.json")));

        //Извлечение Id и других данных из Json
        Map<String, Object> entityData = new ObjectMapper().readValue(jsonBody, Map.class);
       String entityId = (String) entityData.get("id") ;

        given()
                .header("Content-type", "application/json")
                .and()
                .body(jsonBody)
                .when()
                .patch("/api/patch/" + entityId)
                .then()
                .statusCode(204)
                .extract().response();

        //Проверка на обновление данных сущности
        given()
                .when()
                .get("/api/get/" + entityId)
                .then()
                .statusCode(200)
                .body("title", equalTo("Обновлённая сущность"))
                .body("id", equalTo(46));

    }
}
