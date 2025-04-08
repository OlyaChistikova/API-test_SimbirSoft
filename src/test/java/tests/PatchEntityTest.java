package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojo.Addition;
import io.qameta.allure.Step;
import pojo.Entity;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Тесты для обновления сущностей через PATCH запрос.
 */
public class PatchEntityTest extends BaseTest {

    private Entity createEntity;
    private int entityId;

    @BeforeMethod
    @Step("Create entity before updating")
    public void createEntityBeforeUpdateEntity() {
        // Создаем объект Entity
        createEntity = Entity.builder()
                .id(4)
                .addition(Addition.builder()
                        .additional_info("Обновленные сведения")
                        .additional_number(123)
                        .build())
                .importantNumbers("456, 789")
                .title("Обновленная сущность")
                .verified(true)
                .build();

        //Получение id сущности
        entityId = createEntity.getId();

        //Проверяем на существование сущности с заданным id
        given()
                .spec(requestSpecification)
                .contentType("application/json")
                .body(createEntity)
                .when()
                .get(GET_USER_PATH + entityId)
                .then()
                .statusCode(200)
                .extract().response();
    }

    /**
     * Тест для обновления сущности по ID.
     */
    @Test
    @Step("Patch entity by ID")
    public void testPatchEntityById() {
        // Обновляем данные сущности с заданным id
        given()
                .spec(requestSpecification)
                .header("Content-type", "application/json")
                .body(createEntity)
                .when()
                .patch(PATCH_USER_PATH + entityId)
                .then()
                .statusCode(204)
                .extract().response();

        // Проверяем обновление данных сущности
        given()
                .spec(requestSpecification)
                .when()
                .get(GET_USER_PATH + entityId)
                .then()
                .statusCode(200)
                .body("title", equalTo("Обновленная сущность"))
                .body("id", equalTo(entityId));
    }
}