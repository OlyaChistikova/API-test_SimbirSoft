package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.BaseRequests;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import pojo.CreateEntity;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;


public class CreateEntityTest extends BaseTest{

    @Test
    public void testCreateEntityWithSerialization() throws IOException {

        String jsonBody = new String(Files.readAllBytes(Paths.get("src/test/resources/json/entityCreationBody.json")));
        Response createResponse = given()
                .spec(requestSpecification)
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post("/api/create")
                .then()
                .statusCode(200)
                .extract().response();
        //Получаем id только что созданной сущности
        entityId = createResponse.asString();

        // Получение всех сущностей, чтобы проверить создание новой по id
        Response allEntitiesResponse = given()
                .spec(requestSpecification)
                .when()
                .get("/api/getAll")
                .then()
                .statusCode(200)
                .extract().response();

        // Проверка, что ожидаемая сущность присутствует
        allEntitiesResponse.then()
                .body("entity[-1].title", Matchers.equalTo("Тестовая сущность"))
                .log();
    }
//не доделан еще
    @Test
    public void testCreateEntityWithSerializationAndDeserialization() throws IOException {
        CreateEntity createEntity = CreateEntity.builder().build();
        Response createResponse = given()
                .spec(requestSpecification)
                .contentType("application/json")
                .body(createEntity)
                .when()
                .post("/api/create")
                .then()
                .statusCode(200)
                .extract().response();

        ObjectMapper objectMapper = new ObjectMapper();
        //CreatedEntity createdEntity = objectMapper.readValue(createResponse.getBody().asString(), Integer.class);
        //entityId = String.valueOf(createdEntity.getId());
        System.out.println("Id: " + entityId);// Предполагается, что создаваемая сущность возвращает свой ID

        Response allEntitiesResponse = given()
                .spec(requestSpecification)
                .when()
                .get("/api/getAll")
                .then()
                .statusCode(200)
                .extract().response();

        // Проверка, присутствует ли созданный ID в списке
    }

    @AfterMethod
    public void deleteUserAfterCreation(){BaseRequests.deleteUserById(entityId);}
}
